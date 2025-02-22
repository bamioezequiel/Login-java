# 🔐 Sistema de Login con Roles

## Descripción
Sistema de autenticación en Java con una Interfaz Gráfica de Usuario (IGU) que permite la gestión de usuarios y roles. Implementa JPA y MySQL para la persistencia de datos. Cuenta con dos tipos de usuarios:
- **Administrador (admin):** Puede crear, modificar, eliminar y visualizar todos los usuarios.
- **Usuario común (user):** Solo puede visualizar a los usuarios de su mismo tipo.

## 📸 Captura de Pantalla
![Login Preview]()

## 🚀 Características
- 🖥️ Interfaz gráfica con Java Swing.
- 🔒 Validación de usuario y contraseña.
- 🏷️ Sistema de roles (admin y user).
- 🛠️ Operaciones CRUD para la gestión de usuarios.
- 🗄️ Persistencia de datos con JPA y MySQL.

## 📂 Estructura del Proyecto
```
Login
│── com.bamioezequiel.login
│   ├── Login.java
│── com.bamioezequiel.login.igu
│   ├── frmLogin.java
│   ├── frmAdmin.java
│   ├── frmUser.java
│── com.bamioezequiel.login.logica
│   ├── Controller.java
│   ├── User.java
│   ├── Rol.java
│── com.bamioezequiel.login.persistences
│   ├── ControllerPersistence.java
│   ├── UserJpaController.java
│   ├── RolJpaController.java
│── persistence.xml
```

## ⚙️ Configuración
### 1️⃣ Base de Datos
Crear una base de datos en MySQL llamada `login` y ejecutar el siguiente script:
```sql
CREATE DATABASE login;
USE login;

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role ENUM('admin', 'user') NOT NULL
);
```

### 2️⃣ Configurar `persistence.xml`
Ubicado en `src/META-INF/persistence.xml`, debe contener:
```xml
<persistence version="2.2" xmlns="http://xmlns.jcp.org/xml/ns/persistence">
  <persistence-unit name="loginPU" transaction-type="RESOURCE_LOCAL">
    <provider>org.eclipse.persistence.jpa.PersistenceProvider</provider>
    <class>com.bamioezequiel.login.logica.User</class>
    <class>com.bamioezequiel.login.logica.Rol</class>
    <properties>
      <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/login?serverTimezone=UTC"/>
      <property name="javax.persistence.jdbc.user" value="root"/>
      <property name="javax.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
      <property name="javax.persistence.jdbc.password" value=""/>
      <property name="javax.persistence.schema-generation.database.action" value="create"/>
    </properties>
  </persistence-unit>
</persistence>
```

## 🛠️ Tecnologías Utilizadas
- **Java 8+**
- **Swing (Interfaz Gráfica)**
- **JPA con EclipseLink**
- **MySQL**
- **NetBeans / IntelliJ IDEA**

## 📜 Uso
1. Clonar el repositorio:
   ```sh
   git clone https://github.com/bamioezequiel/Login-java
   ```
2. Importar el proyecto en NetBeans o IntelliJ IDEA.
3. Configurar la base de datos y `persistence.xml`.
4. Ejecutar la aplicación.

## 📌 Autor
[Ezequiel Bamio](https://github.com/bamioezequiel) 🚀

