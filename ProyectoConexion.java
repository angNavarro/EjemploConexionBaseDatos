import java.sql.*;

public class ProyectoConexion {
private final String url="jbdc:sqlite:database.db"

    // Conexion a base de datos
    private Connection connect() {
        Connection conn = null; // Variable
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // Create new table
    public void createNewTable() {
        String sql = "CREATE TABLE IF NOT EXISTS alumnos (\n" +
                "id integer PRIMARY KEY,\n" +
                "nombre text NOT NULL,\n" +
                "carrera text NOT NULL,\n" +
                ");";

        try (
                Connection conn = this.connect();
                Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Insert new row into alumnos table
    public void insert(String nombre, String carrera) {
        String sql = "INSERT INTO alumnos(nombre, carrera) VALUES(?,?)";

        try (
                Connection conn = this.connect();
                PreparedStatement pstmt = conn.PreparedStatement(sql)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, carrera);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    // Select all rows in alumnos table
    public void selectAll() {
        String sql = "SELECT id, nombre, carrera FROM alumnos";

        try (
                Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("nombre") + "\t" +
                        rs.getString("carrera"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Update data of alumno specified by id
    public void update(int id, String nombre, String carrera) {
        String sql = "UPDATE alumnos SET nombre = ?, carrera = ? WHERE id = ?";

        try (
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, carrera);
            pstmt.setString(3, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    // Delete alumno specified by id
    public void delete(int id) {
        String sql = "DELETE FROM alumnos WHERE ID=?";

        try (
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        ProyectoConexion app = new ProyectoConexion();

        // Create new table
        app.createNewTable();

        // Insert new data
        app.insert("Angel Aldarias", "Ingenieria software");
        app.insert("Eva Nuez", "Teleco");

        // Select all data
        System.out.println("Lista alumnos: ");
        app.selectAll();

        // Update data
        app.update(1, "Juan perez", "Teleco");
        System.out.println("Datos actualizados: ");
        app.selectAll();

        // Delete student
        app.delete(2);
        System.out.println("Listado de alumnos actualizado: ");
        app.selectAll();
    }

}