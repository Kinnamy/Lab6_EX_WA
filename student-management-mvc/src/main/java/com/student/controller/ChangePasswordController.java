package com.student.controller;

import com.student.dao.UserDAO;
import com.student.model.User;
import org.mindrot.jbcrypt.BCrypt;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/change-password")
public class ChangePasswordController extends HttpServlet {
    
    private UserDAO userDAO;
    
    @Override
    public void init() {
        userDAO = new UserDAO();
    }
    
    // GET: Show the form
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Security check: If not logged in, go to login
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }
        
        request.getRequestDispatcher("/views/change-password.jsp").forward(request, response);
    }
    
    // POST: Process the password change
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Get current user from session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }
        User user = (User) session.getAttribute("user");
        
        // 2. Get form parameters
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // 3. Validation Logic
        String error = null;
        
        if (currentPassword == null || newPassword == null || confirmPassword == null ||
            currentPassword.isEmpty() || newPassword.isEmpty()) {
            error = "All fields are required";
        }
        // Validate 1: Check if new password matches confirm password
        else if (!newPassword.equals(confirmPassword)) {
            error = "New passwords do not match";
        }
        // Validate 2: Check length (Min 8 chars)
        else if (newPassword.length() < 8) {
            error = "New password must be at least 8 characters";
        }
        // Validate 3: Check if Current Password is correct (Using BCrypt)
        // We compare the typed 'currentPassword' against the hashed password stored in the User object
        else if (!BCrypt.checkpw(currentPassword, user.getPassword())) {
            error = "Current password is incorrect";
        }
        
        // 4. If there was an error, send back to form
        if (error != null) {
            request.setAttribute("error", error);
            request.getRequestDispatcher("/views/change-password.jsp").forward(request, response);
            return;
        }
        
        // 5. Success! Hash the NEW password
        String newHashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        
        // 6. Update Database
        if (userDAO.updatePassword(user.getId(), newHashedPassword)) {
            
            // CRITICAL: Update the user object in the session so they don't have to re-login immediately
            user.setPassword(newHashedPassword);
            session.setAttribute("user", user);
            
            request.setAttribute("message", "Password changed successfully!");
            request.getRequestDispatcher("/views/change-password.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Database error. Please try again.");
            request.getRequestDispatcher("/views/change-password.jsp").forward(request, response);
        }
    }
}