/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Picture;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.imgscalr.Scalr;
import session.PictureFacade;

/**
 *
 * @author jvm
 */
@WebServlet(name = "UploadServlet", urlPatterns = {
    "/uploadFile", 
    "/showUploadFile"
})
@MultipartConfig()
public class UploadServlet extends HttpServlet {
    @EJB private PictureFacade pictureFacade;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String path = request.getServletPath();
        switch (path) {
            case "/showUploadFile":
                request.getRequestDispatcher("/uploadFile.jsp").forward(request, response);
                break;
            case "/uploadFile":
                List<Part> fileParts = request.getParts()
                .stream()
                .filter( part -> "file".equals(part.getName()))
                .collect(Collectors.toList());
                String imagesFolder = "D:\\UploadDir\\WebPasswordManager";
//                String imagesFolder = ResourceBundle.getBundle("resouces/directories").getString("uploadDir");
                for(Part filePart : fileParts){
                    String pathToFile = imagesFolder + File.separatorChar
                                    +getFileName(filePart);
                    
                    File tempFile = new File(imagesFolder+File.separatorChar+"tmp"+File.separatorChar+getFileName(filePart));
                    tempFile.mkdirs();
                    try(InputStream fileContent = filePart.getInputStream()){
                       Files.copy(
                               fileContent,tempFile.toPath(), 
                               StandardCopyOption.REPLACE_EXISTING
                       );
                       writeToFile(resize(tempFile),pathToFile);
                       tempFile.delete();
                    }
                    String description = request.getParameter("description");
                    Picture picture = new Picture();
                    picture.setDescription(description);
                    picture.setPathToFile(pathToFile);
                    pictureFacade.create(picture);
                }    
                request.setAttribute("info", "Файл успешно сохранен");
                request.getRequestDispatcher("/uploadFile.jsp").forward(request, response);
                break;
            
        }
    }
    private String getFileName(Part part){
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")){
            if(content.trim().startsWith("filename")){
                return content
                        .substring(content.indexOf('=')+1)
                        .trim()
                        .replace("\"",""); 
            }
        }
        return null;
    }
    public void writeToFile(byte[] data, String fileName) throws IOException{
        try (FileOutputStream out = new FileOutputStream(fileName)) {
            out.write(data);
        }
    }
    public byte[] resize(File icon) {
        try {
           BufferedImage originalImage = ImageIO.read(icon);
           originalImage= Scalr.resize(originalImage, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH,400);
            //To save with original ratio uncomment next line and comment the above.
            //originalImage= Scalr.resize(originalImage, 153, 128);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (Exception e) {
            return null;
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
