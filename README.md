# Task Management System

A comprehensive JavaFX-based Task Management System with file-based storage (JSON) for managing tasks, employees, projects, and attendance.

## Features

### 1. Admin Module
- **User Management**: Add, update, delete users with different roles (Admin, Leader, Employee)
- **Employee Management**: Manage employee profiles, types, departments, and salaries
- **Employee Types**: Developer, Designer, QA, Manager, Team Lead, DevOps
- **Project Management**: Create and manage projects/customers
- **Task Phases**: Configure task phases (Pending, Under Work, Test, Evaluation, Completed, Canceled)
- **Login/Logout**: Secure authentication with BCrypt password hashing

### 2. Tasks Module
- **Task Management**: Create, view, update, and delete tasks
- **Task Details**: Code, title, description, assigned employee, task phase, project, priority, creator, dates, estimation hours
- **Task Logs**: Record actual time spent on tasks (from time, to time, employee)
- **Calendar View**: Visual representation of all employee tasks and phases
- **Role-Based Access**: 
  - Employees can only see their assigned tasks
  - Leaders can manage all tasks (create, show all, evaluate, reassign, change fields)
- **Priority Levels**: Low, Medium, High, Critical

### 3. Employee Module
- **Timecards**: Record attendance and departure times
- **Mission Requests**: Request missions or permissions with approval workflow
- **Leave Types**: Annual Leave, Sick Leave, Emergency Leave, Maternity Leave, Paternity Leave, Unpaid Leave
- **Leave Requests**: Submit leave requests with approval workflow
- **Approval System**: Admin/Leader can approve or reject mission and leave requests


## Project Structure

```
TaskManagementSystem/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/taskmanagement/
│       │       ├── Main.java
│       │       ├── model/
│       │       │   ├── User.java
│       │       │   ├── Employee.java
│       │       │   ├── Task.java
│       │       │   ├── Project.java
│       │       │   ├── TaskLog.java
│       │       │   ├── Timecard.java
│       │       │   ├── LeaveRequest.java
│       │       │   └── MissionRequest.java
│       │       ├── view/
│       │       │   ├── LoginView.java
│       │       │   ├── MainDashboard.java
│       │       │   ├── UserManagementView.java
│       │       │   ├── EmployeeManagementView.java
│       │       │   ├── ProjectManagementView.java
│       │       │   ├── TaskManagementView.java
│       │       │   ├── TaskFormView.java
│       │       │   ├── TaskLogView.java
│       │       │   ├── TimecardView.java
│       │       │   ├── LeaveRequestView.java
│       │       │   ├── MissionRequestView.java
│       │       │   └── ApprovalView.java
│       │       └── util/
│       │           ├── FileManager.java
│       │           ├── SessionManager.java
│       │           ├── PasswordUtil.java
│       │           ├── LocalDateTimeAdapter.java
│       │           └── LocalDateAdapter.java
│       └── resources/
│           ├── fxml/
│           ├── css/
│           └── images/
├── data/
│   ├── users.json
│   ├── employees.json
│   ├── tasks.json
│   ├── projects.json
│   ├── tasklogs.json
│   ├── timecards.json
│   ├── leaverequests.json
│   └── missionrequests.json
├── pom.xml
└── README.md
```

## Installation & Setup

1. Clone the repository:
```bash
git clone https://github.com/mohamedamrelashry/Final_Task_Manager.git
cd TaskManagementSystem
```

2. Compile the project:
```bash
mvn clean compile
```

3. Package the application:
```bash
mvn package
```

4. Run the application:
```bash
mvn javafx:run
```

## Default Credentials

On first run, a default admin account is created:
- **Username**: admin
- **Password**: admin123

**Important**: Change the default password after first login!

## Usage Guide

### For Administrators
1. Login with admin credentials
2. Navigate to Admin Module to:
   - Create users and assign roles
   - Add employees and link them to user accounts
   - Create projects/customers
   - Manage all system data

### For Leaders
1. Login with leader credentials
2. Access Tasks Module to:
   - Create and assign tasks to employees
   - Monitor task progress and phases
   - Review task logs
   - Evaluate completed tasks
3. Access Approval Center to:
   - Approve/reject leave requests
   - Approve/reject mission/permission requests

### For Employees
1. Login with employee credentials
2. View "My Tasks" to see assigned tasks
3. Log time spent on tasks
4. Record attendance (check-in/check-out)
5. Submit leave requests
6. Submit mission/permission requests

## Data Storage

All data is stored in JSON files in the `data/` directory:
- **users.json**: User accounts and authentication
- **employees.json**: Employee profiles
- **tasks.json**: Task information
- **projects.json**: Project/customer data
- **tasklogs.json**: Time tracking logs
- **timecards.json**: Attendance records
- **leaverequests.json**: Leave requests
- **missionrequests.json**: Mission and permission requests



