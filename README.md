# J2EE Task Management System

## Overview
This project is a **Simple Task Management System** built using **J2EE, Servlets, and JSP**. It is designed to manage tasks within a company hierarchy, including **Managers, Team Leaders, and Developers**. Each role has specific permissions and responsibilities for task assignment and management.

## Features
- **User Authentication**: Login and logout functionality for employees.
- **Role-based Access**:
  - **Managers**: Full access to all team leaders, developers, and tasks.
  - **Team Leaders**: Manage assigned developers and assign new tasks.
  - **Developers**: View and update task statuses.
- **Task Management**:
  - Users can create tasks.
  - Team leaders and managers can approve tasks.
  - Tasks are auto-assigned based on hierarchy.
- **Dynamic Hierarchy View**: Managers can see the full structure of their team.
- **Sortable Task List**: Task/user lists are presented in sortable tables for better organization.

## Project Structure
- **Database Setup**: Schema for users, roles, and tasks.
- **Database Connection**: JDBC-based connection handling.
- **Authentication System**: Secure login/logout implementation.
- **Core Web Pages**:
  - Home
  - Login/Logout
  - Task Management Dashboard
  - Employee List

## Technologies Used
- **Java (J2EE)**
- **Servlets & JSP**
- **JDBC (Database Connectivity)**
- **HTML, CSS, JavaScript**
- **Tomcat Server**

## Usage
- Login with a valid account.
- Perform actions based on your role.
- Manage and track tasks in the system.

## Author
**Prepared by:** Fathi Hindi - Infinite Tiers, Inc.

