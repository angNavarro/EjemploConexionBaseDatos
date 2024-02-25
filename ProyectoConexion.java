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

        try (Connection conn = this.connect();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Insert new row into alumnos table
    public void insert(String nombre, String carrera) {
        String sql = "INSERT INTO alumnos(nombre, carrera) VALUES(?,?)";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.PreparedStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, carrera);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}}