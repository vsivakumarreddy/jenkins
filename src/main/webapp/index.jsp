<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Updated Customer App Welcome</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
            background-color: #f4f4f4;
            color: #333;
            text-align: center;
        }
        h1 {
            color: #0056b3;
            margin-bottom: 20px;
        }
        p {
            font-size: 1.1em;
            line-height: 1.6;
        }
        .button-container {
            margin-top: 30px;
        }
        .button {
            display: inline-block;
            padding: 12px 25px;
            font-size: 1.1em;
            color: #fff;
            background-color: #28a745;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .button:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <h1>Welcome to the Refreshed Manikiran App!</h1>
    <p>We've made some exciting updates to enhance your experience.</p>
    <p>This is the main entry point for your web application.</p>

    <div class="button-container">
        <p>Click the button below to interact with our simple servlet:</p>
        <a href="hello" class="button">Go to Hello Servlet</a>
    </div>

    <p style="margin-top: 50px; font-size: 0.9em; color: #666;">
        Built with Java, Maven, and deployed on Tomcat.
    </p>
</body>
</html>
