# Quick Start Guide - Task Management System (Refactored)

## 🚀 Getting Started in 5 Minutes

### Step 1: Build the Project
```bash
cd Task_Manager_Refactored
mvn clean package -DskipTests
```

### Step 2: Run the Application
```bash
java -jar target/TaskManagementSystem-2.0-REFACTORED.jar
```

### Step 3: Login
- **Username**: `admin`
- **Password**: `admin123`

## 📋 Main Features

### For Administrators
1. **Manage Users**
   - Add new users with roles (Admin, Leader, Employee)
   - Update user details and passwords
   - Deactivate users

2. **Manage Employees**
   - Create employee records
   - Assign users to employees
   - Set salary and department information
   - Track employee types (Developer, Designer, QA, Manager, Analyst)

3. **Manage Projects**
   - Create projects with descriptions
   - Track project status (Active, Completed, On Hold, Cancelled)
   - Assign customers to projects

### For Leaders
1. **Create and Manage Tasks**
   - Create new tasks with all details
   - Assign tasks to employees
   - Track task phases (Pending, Under Work, Test, Evaluation, Completed, Cancelled)
   - Set priority levels (Low, Medium, High, Critical)

2. **Monitor Task Progress**
   - View all tasks in the system
   - Track actual hours vs. estimation
   - Record task logs (time tracking)

3. **Approve Requests**
   - Review and approve/disapprove leave requests
   - Review and approve/disapprove mission requests

### For Employees
1. **View My Tasks**
   - See tasks assigned to you
   - Track task phases and deadlines

2. **Record Time**
   - Log hours spent on tasks
   - View task logs

3. **Submit Requests**
   - Submit leave requests
   - Submit mission/permission requests

4. **Track Attendance**
   - Record timecards (clock in/out)

## 🎯 Common Workflows

### Creating a New Task
1. Login as Admin or Leader
2. Click "Create Task" in the sidebar
3. Fill in task details:
   - Task Code (unique identifier)
   - Title and Description
   - Assign to an employee
   - Select phase, project, and priority
   - Set start and end dates
   - Enter estimation hours
4. Click "Create Task"

### Recording Task Time
1. Click "Task Logs" in the sidebar
2. Select the task and employee
3. Select the date
4. Enter from and to times
5. Click "Add Log"

### Managing Users
1. Login as Admin
2. Click "Manage Users" in the sidebar
3. View all users in the table
4. Click on a user to select it
5. Modify details and click "Update User"
6. Or click "Delete User" to remove

## 📁 Project Structure

```
src/main/java/com/taskmanagement/
├── Main.java                    # Application entry point
├── controller/                  # All screen controllers
├── model/                       # Data models
├── util/                        # Utility functions
└── service/                     # Business logic (future)

src/main/resources/
├── fxml/                        # UI definitions
│   ├── login.fxml
│   ├── dashboard.fxml
│   ├── admin/
│   ├── tasks/
│   └── employee/
├── css/                         # Stylesheets
└── data/                        # JSON data files
```

## 🔑 Key Concepts

### Roles
- **ADMIN**: Full system access, manage users and employees
- **LEADER**: Can create and manage tasks, approve requests
- **EMPLOYEE**: Can view assigned tasks, submit requests

### Task Phases
- **Pending**: Task not started
- **Under Work**: Task in progress
- **Test**: Task in testing phase
- **Evaluation**: Task under evaluation
- **Completed**: Task finished
- **Cancelled**: Task cancelled

### Priority Levels
- **Low**: Non-urgent tasks
- **Medium**: Regular priority
- **High**: Important tasks
- **Critical**: Urgent tasks

## 💾 Data Files

All data is stored in JSON format in `src/main/resources/data/`:
- `users.json`: User accounts
- `employees.json`: Employee information
- `projects.json`: Projects
- `tasks.json`: Tasks
- `task_logs.json`: Time logs
- `timecards.json`: Attendance records

## ⚙️ Configuration

### Changing Default Admin Password
1. Edit `Main.java`
2. Find the `initializeDefaultData()` method
3. Change the password in this line:
   ```java
   PasswordUtil.hashPassword("admin123")
   ```
4. Rebuild the project

## 🐛 Troubleshooting

### Application won't start
- Ensure Java 11+ is installed: `java -version`
- Check Maven is installed: `mvn -version`
- Try rebuilding: `mvn clean package`

### Login fails
- Verify username and password are correct
- Check if user is active (not deactivated)
- Default credentials: admin / admin123

### Data not saving
- Check file permissions in `src/main/resources/data/`
- Ensure JSON files are not corrupted
- Try deleting JSON files to reset data

## 📚 Next Steps

1. Create a new admin user for security
2. Add employees to the system
3. Create projects
4. Create tasks and assign them
5. Start tracking time

## 🎓 Learning Resources

- **JavaFX Documentation**: https://openjfx.io/
- **FXML Guide**: https://docs.oracle.com/javase/8/javafx/fxml-tutorial/
- **Maven Guide**: https://maven.apache.org/guides/

---

**Need Help?** Check the README_REFACTORED.md for detailed documentation.
