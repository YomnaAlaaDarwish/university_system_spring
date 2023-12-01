<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Management System</title>
    <link rel="stylesheet" href="styles.css"> <!-- إذا كنت تستخدم ملف CSS منفصل -->
    <style>
     body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        text-align: center;
        padding-top: 100px; /* زيادة المساحة العلوية */
    }

    .container {
        display: inline-block;
        padding: 40px; /* زيادة الحشوة لجعل الحاوية أكبر */
        background-color: white;
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        width: 80%; /* زيادة عرض الحاوية */
        max-width: 800px; /* تحديد أقصى عرض */
    }

    h1 {
        color: #333;
        font-size: 32px; /* تكبير حجم الخط للعنوان */
        margin-bottom: 30px;
    }

    .buttons button {
        padding: 15px 30px; /* تكبير حجم الأزرار */
        margin: 15px;
        font-size: 20px; /* تكبير حجم الخط داخل الأزرار */
        border: none;
        border-radius: 8px; /* جعل الزوايا أكثر تقريبًا */
        cursor: pointer;
        transition: background-color 0.3s;
    }

    .buttons button:hover {
        background-color: #007bff; /* تغيير لون الخلفية عند تمرير الماوس */
        color: white;
    }
    </style>
</head>
<body>
    <div class="container">
        <h1>Student Management System</h1>
        <div class="buttons">
            <button onclick="location.href='Add'">Add</button>
            <button onclick="location.href='search'">Search</button>
            <button onclick="location.href='/Delete'">Delete</button>
            <button onclick="location.href='/Sort'">Sort</button>
            <button onclick="location.href='UpdateStud.jsp'">Update Student</button>
        </div>
    </div>
</body>
</html>