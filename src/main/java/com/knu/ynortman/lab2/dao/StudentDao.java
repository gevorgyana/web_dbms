package com.knu.ynortman.lab2.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.knu.ynortman.lab2.exception.ServerException;
import com.knu.ynortman.lab2.model.Student;
import com.knu.ynortman.lab2.util.JdbcConnection;

public class StudentDao {

  // hardcode the statements for now to check how it all works
  private static final Logger logger = LogManager.getRootLogger();
  private static final String allStudentsQuery = "";
  private static final String idStudentQuery = "";
  private static final String addStudentQuery = "";
  private static final String deleteStudentQuery = "";
  private static final String updateStudentQuery = "";

  // checked and should work fine
  public static Student getStudentById(int id) throws ServerException {
    Student student = null;
    try(Connection conn = JdbcConnection.getConnection()) {
      PreparedStatement preparedStatement = conn.prepareStatement(idStudentQuery);
      ResultSet rs = preparedStatement.executeQuery();
      if(rs.next()) {
        student = studentFromResultSet(rs);
      }
    } catch (SQLException | IOException e) {
      logger.error("Cannot get all students");
      throw new ServerException();
    }
    return student;
  }

  // checked and should work fine
  public static Student insertStudent(Student student) throws ServerException {
    if(student == null) return null;
    try(Connection conn = JdbcConnection.getConnection()) {
      PreparedStatement ps = conn.prepareStatement(addStudentQuery);
      int rows = ps.executeUpdate();
      if(rows > 0) {
        logger.debug("Student inserted");
      } else {
        logger.warn("Student was not inserted");
        return null;
      }
    } catch (SQLException | IOException e) {
      logger.error("Error in adding student");
      throw new ServerException();
    }
    return student;
  }

  // checked and should work fine
  public static void deleteStudent(int id) throws ServerException {
    try(Connection conn = JdbcConnection.getConnection()) {
      PreparedStatement ps = conn.prepareStatement(deleteStudentQuery);
      int rows = ps.executeUpdate();
      if(rows <= 0) {
        logger.warn("Cannot delete student");
      }
    } catch (SQLException | IOException e) {
      logger.error("Error deleting student");
      throw new ServerException();
    }
  }

  public static Student updateStudent(Student student) throws ServerException {
    if(student == null) return null;
    try(Connection conn = JdbcConnection.getConnection()) {
      PreparedStatement ps = conn.prepareStatement(updateStudentQuery);
      if(ps.executeUpdate() <= 0) {
        logger.error("Cannot update student");
        return null;
      }
    } catch (SQLException | IOException e) {
      logger.error("Cannot update student");
      throw new ServerException();
    }
    return student;
  }
}
