<html>
<head>
    <title>Student Record Sorting</title>
    <script>
        function fetchSortedStudents() {
        var sortAttribute = document.getElementById("sortAttribute").value;
        var sortOrder = document.getElementById("sortOrder").value;

        fetch('/sortStudents?attribute=' + sortAttribute + '&order=' + sortOrder)
            .then(response => response.text())
            .then(data => {
                document.getElementById("sortedStudents").innerText = data;
            })
            .catch(error => {
                console.error('Error:', error);
        });
}

    </script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        form {
            width: 50%; /* تكبير حجم النموذج */
            max-width: 600px; /* تحديد الحد الأقصى لعرض النموذج */
            text-align: center;
            background-color: white;
            padding: 40px; /* زيادة الحشو داخل النموذج */
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        h1 {
            color: #333;
            font-size: 36px; /* تكبير حجم خط العنوان */
            margin-bottom: 20px; /* تعديل المسافة أسفل العنوان */
        }

        label {
            margin-right: 15px;
            font-weight: bold;
            font-size: 22px; /* تكبير حجم خط التسميات */
        }

        select, button {
            margin: 10px 0; /* تعديل المسافات حول العناصر */
            padding: 15px 20px; /* تكبير الحشو للأزرار وقوائم التحديد */
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #fff;
            cursor: pointer;
            font-size: 20px; /* تكبير حجم الخط */
            width: 200px; /* تحديد عرض ثابت للأزرار وقوائم التحديد */
        }

        button {
            background-color: #5cb85c;
            color: white;
            border-color: #4cae4c;
        }

        button:hover {
            background-color: #4cae4c;
        }

    </style>
</head>
<body>
    <form>
        <h1>Student Records</h1>

        <label for="sortAttribute">Sort by:</label>
        <select id="sortAttribute">
            <option value="ID">ID</option>
            <option value="FirstName">FirstName</option>
            <option value="LastName">LastName</option>
            <option value="Gender">Gender</option>
            <option value="GPA">GPA</option>
            <option value="Level">Level</option>
            <option value="Address">Address</option>
        </select><br>

        <label for="sortOrder">Sort Order:</label>
        <select id="sortOrder">
            <option value="ascending">ascending</option>
            <option value="descending">descending</option> 
        </select><br>

        <button type="button" onclick="fetchSortedStudents()">Sort</button>
        <p id="sortedStudents"></p>
    </form>
  
</body>
</html>
