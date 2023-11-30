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

        table {
            border-collapse: collapse;
            width: 80%;
            margin: 20px auto;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #007bff;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        #studentCount {
            font-weight: bold;
            margin-bottom: 10px;
        }
    </style>
    <body>
        <label for="value">Student address:</label>
        <input type="text" id="value">
        <button onclick="fetchStudentInfo()">Get Student Info</button>
    
        <p id="studentCount">Number of Students: 0</p>
    
        <table id="studentTable">
            <thead>
                <tr>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Gender</th>
                    <th>GPA</th>
                    <th>Level</th>
                    <th>Address</th>
                </tr>
            </thead>
            <tbody id="studentInfo">
                <!-- Student data will be displayed here dynamically -->
            </tbody>
        </table>
    
        <script>
            function fetchStudentInfo() {
                var value = document.getElementById("value").value;
                fetch('/address?address=' + value)
                    .then(response => response.json())
                    .then(data => {
                        displayStudentInfo(data);
                    })
                    .catch(error => {
                        console.error('Error:', error);
                    });
            }
    
            function displayStudentInfo(students) {
                var tableBody = document.getElementById("studentInfo");
                var countElement = document.getElementById("studentCount");
    
                tableBody.innerHTML = "";
                countElement.textContent = "Number of Students: " + students.length;
    
                students.forEach(student => {
                    var row = tableBody.insertRow();
    
                    var cell1 = row.insertCell(0);
                    var cell2 = row.insertCell(1);
                    var cell3 = row.insertCell(2);
                    var cell4 = row.insertCell(3);
                    var cell5 = row.insertCell(4);
                    var cell6 = row.insertCell(5);
    
                    cell1.innerHTML = student.firstName;
                    cell2.innerHTML = student.lastName;
                    cell3.innerHTML = student.gender;
                    cell4.innerHTML = student.gpa;
                    cell5.innerHTML = student.level;
                    cell6.innerHTML = student.address;
                });
            }
        </script>
    </body>
    
</html>
