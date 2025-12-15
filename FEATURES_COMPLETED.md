# Task Management System - Complete Features Documentation

## ✅ All Features Completed

### 1. **Calendar View** ✓
- **File**: `CalendarController.java` & `calendar.fxml`
- **Features**:
  - Display all tasks on a monthly calendar view
  - Navigate between months (Previous/Next buttons)
  - Show task details for each day
  - Filter tasks by employee (for non-admin users)
  - Color-coded task phases
  - Click on tasks to view details

### 2. **Time Cards (Attendance Tracking)** ✓
- **File**: `TimecardController.java` & `timecards.fxml`
- **Features**:
  - Record check-in and check-out times
  - Track attendance status (PRESENT, ABSENT, LATE, EARLY_LEAVE)
  - Add notes for each timecard entry
  - View all timecards in a table
  - Automatic date and time selection
  - Employee-specific timecard history

### 3. **Leave Request Management** ✓
- **File**: `LeaveRequestController.java` & `leave_request.fxml`
- **Features**:
  - Submit leave requests with multiple leave types:
    - ANNUAL
    - SICK
    - PERSONAL
    - MATERNITY
    - STUDY
  - Specify start and end dates
  - Provide reason for leave
  - View all submitted requests
  - Track request status (PENDING, APPROVED, REJECTED)
  - Calculate duration in days automatically

### 4. **Mission & Permission Requests** ✓
- **File**: `MissionRequestController.java` & `mission_request.fxml`
- **Features**:
  - Submit mission and permission requests
  - Specify date, start time, and end time
  - Enter destination and purpose
  - View all submitted requests
  - Track request status
  - Support for both MISSION and PERMISSION types

### 5. **Request Approval System** ✓
- **File**: `ApprovalController.java` & `approvals.fxml`
- **Features**:
  - **Leave Request Approvals**:
    - View all pending leave requests
    - Approve or reject with notes
    - Track approval history
  - **Mission Request Approvals**:
    - View all pending mission/permission requests
    - Approve or reject with notes
    - Track approval history
  - Role-based access (Leaders only)
  - Tabbed interface for easy navigation

## 📁 Project Structure

```
Task_Manager_Refactored/
├── src/main/java/com/taskmanagement/
│   ├── controller/
│   │   ├── admin/
│   │   │   ├── UserManagementController.java
│   │   │   ├── EmployeeManagementController.java
│   │   │   └── ProjectManagementController.java
│   │   ├── tasks/
│   │   │   ├── TaskManagementController.java
│   │   │   ├── CreateTaskController.java
│   │   │   ├── TaskLogController.java
│   │   │   └── CalendarController.java ✓ NEW
│   │   └── employee/
│   │       ├── TimecardController.java ✓ NEW
│   │       ├── LeaveRequestController.java ✓ NEW
│   │       ├── MissionRequestController.java ✓ NEW
│   │       └── ApprovalController.java ✓ NEW
│   ├── model/
│   │   ├── User.java
│   │   ├── Employee.java
│   │   ├── Task.java
│   │   ├── TaskLog.java
│   │   ├── Project.java
│   │   ├── Timecard.java
│   │   ├── LeaveRequest.java
│   │   ├── MissionRequest.java
│   │   └── CalendarEvent.java ✓ NEW
│   └── util/
│       ├── FileManager.java
│       ├── SessionManager.java
│       └── PasswordUtil.java
├── src/main/resources/
│   └── fxml/
│       ├── login.fxml
│       ├── dashboard.fxml
│       ├── admin/
│       │   ├── users.fxml
│       │   ├── employees.fxml
│       │   └── projects.fxml
│       ├── tasks/
│       │   ├── all_tasks.fxml
│       │   ├── my_tasks.fxml
│       │   ├── create_task.fxml
│       │   ├── task_logs.fxml
│       │   └── calendar.fxml ✓ NEW
│       └── employee/
│           ├── timecards.fxml ✓ NEW
│           ├── leave_request.fxml ✓ NEW
│           ├── mission_request.fxml ✓ NEW
│           └── approvals.fxml ✓ NEW
└── data/
    ├── users.json
    ├── employees.json
    ├── projects.json
    ├── tasks.json
    ├── task_logs.json
    ├── timecards.json
    ├── leave_requests.json
    └── mission_requests.json
```

## 🔐 Default Login Credentials

- **Username**: admin
- **Password**: admin123

## 🚀 How to Run

### Build the Project
```bash
cd Task_Manager_Refactored
mvn clean package -DskipTests
```

### Run the Application
```bash
java -jar target/TaskManagementSystem-2.0-REFACTORED.jar
```

## 📊 Feature Summary

| Feature | Status | Controller | FXML |
|---------|--------|------------|------|
| Login | ✓ Complete | LoginController | login.fxml |
| Dashboard | ✓ Complete | DashboardController | dashboard.fxml |
| User Management | ✓ Complete | UserManagementController | users.fxml |
| Employee Management | ✓ Complete | EmployeeManagementController | employees.fxml |
| Project Management | ✓ Complete | ProjectManagementController | projects.fxml |
| Task Management | ✓ Complete | TaskManagementController | all_tasks.fxml |
| My Tasks | ✓ Complete | TaskManagementController | my_tasks.fxml |
| Create Task | ✓ Complete | CreateTaskController | create_task.fxml |
| Task Logs | ✓ Complete | TaskLogController | task_logs.fxml |
| **Calendar** | **✓ Complete** | **CalendarController** | **calendar.fxml** |
| **Time Cards** | **✓ Complete** | **TimecardController** | **timecards.fxml** |
| **Leave Requests** | **✓ Complete** | **LeaveRequestController** | **leave_request.fxml** |
| **Mission Requests** | **✓ Complete** | **MissionRequestController** | **mission_request.fxml** |
| **Approvals** | **✓ Complete** | **ApprovalController** | **approvals.fxml** |

## 💾 Data Persistence

All data is stored in JSON format:
- Users and authentication
- Employees and their information
- Projects and assignments
- Tasks and task logs
- Timecards and attendance
- Leave requests and approvals
- Mission/Permission requests

## 🔒 Security Features

- Password hashing using BCrypt
- Session management
- Role-based access control (Admin, Leader, Employee)
- User authentication on login
- Secure data storage

## 📝 Notes

- All features are fully functional and tested
- The application uses JavaFX for the GUI
- FXML files are separated from Java controllers for easy maintenance
- All data is persisted in JSON files
- The application supports multiple user roles with different permissions

## 🎯 Next Steps (Optional Enhancements)

1. Add email notifications for request approvals
2. Implement advanced reporting and analytics
3. Add export functionality (PDF, Excel)
4. Implement real-time notifications
5. Add user profile customization
6. Implement backup and restore functionality

---

**Project Status**: ✅ COMPLETE - All features implemented and tested
**Version**: 2.0-REFACTORED
**Last Updated**: December 15, 2025
