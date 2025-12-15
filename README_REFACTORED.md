# Task Management System - Refactored Version

## 📋 Overview

This is a refactored version of the Task Management System, redesigned with a modern **FXML/Controller** architecture for better separation of concerns, maintainability, and scalability. The application is built using **JavaFX 11** with file-based JSON storage.

## 🎯 Key Improvements

### 1. **Separated GUI Files**
- **FXML Files**: All user interface definitions are now in separate `.fxml` files located in `src/main/resources/fxml/`
- **Controller Classes**: Each FXML file has a corresponding controller class in `src/main/java/com/taskmanagement/controller/`
- **Benefit**: Easy to modify UI without touching business logic, and vice versa

### 2. **Organized Project Structure**
```
Task_Manager_Refactored/
├── src/main/java/com/taskmanagement/
│   ├── Main.java                          # Application entry point
│   ├── controller/
│   │   ├── LoginController.java           # Login screen logic
│   │   ├── DashboardController.java       # Main dashboard navigation
│   │   ├── admin/
│   │   │   ├── UserManagementController.java
│   │   │   ├── EmployeeManagementController.java
│   │   │   └── ProjectManagementController.java
│   │   └── tasks/
│   │       ├── TaskManagementController.java
│   │       ├── CreateTaskController.java
│   │       └── TaskLogController.java
│   ├── model/                             # Data models (unchanged)
│   ├── util/                              # Utility classes (unchanged)
│   └── service/                           # Business logic (for future expansion)
├── src/main/resources/
│   ├── fxml/
│   │   ├── login.fxml
│   │   ├── dashboard.fxml
│   │   ├── admin/
│   │   │   ├── users.fxml
│   │   │   ├── employees.fxml
│   │   │   └── projects.fxml
│   │   ├── tasks/
│   │   │   ├── all_tasks.fxml
│   │   │   ├── my_tasks.fxml
│   │   │   ├── create_task.fxml
│   │   │   ├── task_logs.fxml
│   │   │   └── calendar.fxml
│   │   └── employee/
│   │       ├── timecards.fxml
│   │       ├── leave_request.fxml
│   │       ├── mission_request.fxml
│   │       └── approvals.fxml
│   ├── css/                               # Stylesheets (for future use)
│   └── data/                              # JSON data files
└── pom.xml                                # Maven configuration
```

## 🚀 Features Implemented

### Admin Module ✅
- **User Management**: Add, update, delete users with role assignment (ADMIN, LEADER, EMPLOYEE)
- **Employee Management**: Manage employee details, assign users, set salary and department
- **Project Management**: Create and manage projects with status tracking (Active, Completed, On Hold, Cancelled)

### Tasks Module ✅
- **All Tasks View**: Display all tasks with filtering by role
- **My Tasks View**: Employees can see only their assigned tasks
- **Create Task**: Create new tasks with all required details (code, title, description, assignment, phase, priority, dates, estimation hours)
- **Task Logs**: Track time spent on tasks with from/to time recording
- **Calendar**: Placeholder for calendar view (ready for implementation)

### Employee Module ✅
- **Timecards**: Placeholder for attendance tracking
- **Leave Request**: Placeholder for leave request management
- **Mission/Permission Request**: Placeholder for mission and permission requests
- **Approvals**: Placeholder for request approval workflow

### Authentication ✅
- **Login System**: Secure login with username and password
- **Session Management**: User session tracking with role-based access control
- **Default Admin**: Auto-created admin user (username: `admin`, password: `admin123`)

## 🔧 Building and Running

### Prerequisites
- Java 11 or higher
- Maven 3.6+

### Build
```bash
cd Task_Manager_Refactored
mvn clean package
```

### Run
```bash
java -jar target/TaskManagementSystem-2.0-REFACTORED.jar
```

Or use Maven directly:
```bash
mvn javafx:run
```

## 📝 Default Login Credentials
- **Username**: `admin`
- **Password**: `admin123`

## 🎨 Architecture Pattern

### FXML/Controller Pattern
Each screen follows this pattern:

1. **FXML File** (`*.fxml`): Defines the UI structure using XML
   - Components are defined declaratively
   - Easy to modify layout without code changes

2. **Controller Class** (`*Controller.java`): Handles logic and events
   - Annotated with `@FXML` for component injection
   - Contains event handlers and business logic
   - Communicates with models and utilities

### Example: User Management
```
users.fxml (UI Definition)
    ↓
UserManagementController (Logic Handler)
    ↓
FileManager (Data Persistence)
    ↓
User Model (Data Structure)
```

## 📦 Data Storage

All data is stored in JSON files located in `src/main/resources/data/`:
- `users.json`: User accounts and credentials
- `employees.json`: Employee information
- `projects.json`: Project details
- `tasks.json`: Task information
- `timecards.json`: Timecard records
- `task_logs.json`: Task time logs

## 🔐 Security Features

- **Password Hashing**: Uses BCrypt for secure password storage
- **Session Management**: Tracks logged-in user and their role
- **Role-Based Access**: Different features available based on user role
- **Input Validation**: Form validation before data submission

## 🛠️ Technology Stack

- **Framework**: JavaFX 11
- **Build Tool**: Maven
- **Data Format**: JSON (Gson library)
- **Security**: BCrypt for password hashing
- **Language**: Java 11

## 📚 Project Models

### User
- ID, Username, Password (hashed), Role, Email, Active status

### Employee
- ID, Name, Email, Phone, Type, User ID (reference), Department, Salary, Active status

### Project
- ID, Name, Description, Customer Name, Start Date, End Date, Status, Active status

### Task
- Code, Title, Description, Assigned Employee ID, Phase, Project ID, Priority, Creator ID, Start Date, End Date, Estimation Hours, Actual Hours

### TaskLog
- ID, Task Code, Employee ID, From Time, To Time, Hours Spent, Notes

## 🎯 Next Steps for Development

1. **Calendar View**: Implement calendar display for tasks
2. **Leave Management**: Complete leave request workflow
3. **Timecard Management**: Implement attendance tracking
4. **Reporting**: Add reports and analytics
5. **Database Migration**: Consider migrating from JSON to a proper database
6. **UI Styling**: Add CSS stylesheets for better appearance
7. **Email Notifications**: Add email alerts for task assignments
8. **Export Features**: Add PDF/Excel export functionality

## 🐛 Known Limitations

- File-based storage (not suitable for large-scale deployments)
- No concurrent access control (single-user at a time)
- Calendar view not yet implemented
- Leave and mission request workflows are placeholders

## 💡 Tips for Maintenance

1. **Adding a New Feature**:
   - Create a new FXML file in `src/main/resources/fxml/`
   - Create a corresponding controller in `src/main/java/com/taskmanagement/controller/`
   - Add navigation link in `DashboardController.java`

2. **Modifying UI**:
   - Edit the FXML file directly (no need to recompile Java)
   - Use Scene Builder for visual editing if preferred

3. **Adding Business Logic**:
   - Create service classes in `src/main/java/com/taskmanagement/service/`
   - Controllers should delegate complex logic to services

## 📞 Support

For issues or questions about the refactored structure, refer to the controller classes and FXML files for detailed implementation examples.

---

**Version**: 2.0-REFACTORED  
**Last Updated**: December 15, 2025  
**Author**: Manus AI
