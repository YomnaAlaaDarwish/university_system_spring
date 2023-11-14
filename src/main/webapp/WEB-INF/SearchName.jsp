<!DOCTYPE html>
<html>
<head>
    <title>Student Information</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            padding: 20px;
            text-align: center;
        }

        label {
            margin-right: 10px;
            font-size: 18px;
        }

        input[type="text"] {
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
            margin-right: 10px;
        }

        button {
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }

        button:hover {
            background-color: #0056b3;
        }

        #studentInfo {
            margin-top: 20px;
            font-size: 18px;
            color: #333;
            padding: 15px;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            max-width: 600px;
            margin-left: auto;
            margin-right: auto;
            text-align: left;
        }
 
        /* p {
            font-family: 'Arial', sans-serif;
            color: #333;
            background-color: green;
            border: 1px solid #ddd;
            padding: 10px;
            border-radius: 4px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            margin: 10px 0;
            text-align: center;
            max-width: 400px;
           
        } */
    </style>
    <script>
        function fetchStudentInfo() {
            var studentName = document.getElementById("studentName").value;
            fetch('/getStudentInfo?studentName=' + studentName)
                .then(response => response.text())
                .then(data => {
                    document.getElementById("studentInfo").innerText = data;
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        }
    </script>
   
</head>
<body>
    <label for="studentName">Student Name:</label>
    <input type="text" id="studentName">
    <button onclick="fetchStudentInfo()">Get Student Info</button>
    <p id="studentInfo">Student Info</p>
</body>
</html>
