package no.kristiania.database;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberDao {

    private ArrayList<String> members = new ArrayList<>();
    private DataSource dataSource;

    public MemberDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public static void main(String[] args) throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL("jdbc:postgresql://localhost:5432/kristianiashop");
        dataSource.setUser("kristianiashopuser");
        dataSource.setPassword("abc");

        System.out.println("Enter member name: ");
        Scanner scanner = new Scanner(System.in);
        String memberName = scanner.next();

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO members (member_name) VALUES (?)")) {
                statement.setString(1, memberName);
                statement.executeUpdate();
            }
        }

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM members")) {
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        System.out.println(rs.getString("member_name"));
                    }
                }
            }
        }
    }

    public void insert(String member) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO members (member_name) VALUES (?)")) {
                statement.setString(1, member);
                statement.executeUpdate();
            }
        }
        members.add(member);
    }

    public List<String> list() {
        return members;
    }
}
