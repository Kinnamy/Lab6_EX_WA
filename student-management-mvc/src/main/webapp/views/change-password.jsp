<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        .container {
            max-width: 500px;
            margin: 50px auto;
            background: white;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.2);
        }
        h1 { color: #333; text-align: center; margin-bottom: 30px; }
        
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; color: #555; font-weight: 500; }
        input[type="password"] {
            width: 100%; padding: 12px;
            border: 2px solid #ddd; border-radius: 5px;
        }
        
        .btn { 
            width: 100%; 
            padding: 12px; 
            border: none; 
            border-radius: 5px; 
            cursor: pointer; 
            font-size: 16px; 
            font-weight: 600; 
            color: white; 
            text-decoration: none; 
            display: block; 
            text-align: center; 
        }
        
        .btn-primary { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
        .btn-secondary { background-color: #6c757d; margin-top: 10px; }
        
        .message { padding: 15px; margin-bottom: 20px; border-radius: 5px; text-align: center; }
        .success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
    </style>
</head>
<body>

    <div class="container">
        <h1>🔒 Change Password</h1>
        
        <c:if test="${not empty message}">
            <div class="message success">✅ ${message}</div>
        </c:if>
        
        <c:if test="${not empty error}">
            <div class="message error">⚠️ ${error}</div>
        </c:if>

        <form action="change-password" method="post">
            
            <div class="form-group">
                <label>Current Password</label>
                <input type="password" name="currentPassword" required placeholder="Enter current password">
            </div>

            <div class="form-group">
                <label>New Password (Min 8 chars)</label>
                <input type="password" name="newPassword" required placeholder="Enter new password">
            </div>

            <div class="form-group">
                <label>Confirm New Password</label>
                <input type="password" name="confirmPassword" required placeholder="Re-enter new password">
            </div>

            <button type="submit" class="btn btn-primary">Update Password</button>
            <a href="dashboard" class="btn btn-secondary">Back to Dashboard</a>
        </form>
    </div>

</body>
</html>