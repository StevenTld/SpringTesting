import axios from 'axios';

const API_URL = 'http://localhost:8080';

class StudentService {
  // Get all students
  async getAllStudents() {
    try {
      const response = await axios.get(`${API_URL}/students`);
      return response.data;
    } catch (error) {
      console.error('Error fetching students:', error);
      throw error;
    }
  }

  // Get a student by ID
  async getStudentById(id) {
    try {
      const response = await axios.get(`${API_URL}/students/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching student with ID ${id}:`, error);
      throw error;
    }
  }

  // Create a new student
  async createStudent(student) {
    try {
      const response = await axios.post(`${API_URL}/students`, student);
      return response.data;
    } catch (error) {
      console.error('Error creating student:', error);
      throw error;
    }
  }

  // Update a student
  async updateStudent(id, student) {
    try {
      const response = await axios.put(`${API_URL}/students/${id}`, student);
      return response.data;
    } catch (error) {
      console.error(`Error updating student with ID ${id}:`, error);
      throw error;
    }
  }

  // Delete a student
  async deleteStudent(id) {
    try {
      const response = await axios.delete(`${API_URL}/students/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error deleting student with ID ${id}:`, error);
      throw error;
    }
  }

  // Get student groups
  async getStudentGroups(id) {
    try {
      const response = await axios.get(`${API_URL}/students/${id}/groupes`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching groups for student with ID ${id}:`, error);
      throw error;
    }
  }

  // Get student formations
  async getStudentFormations(id) {
    try {
      const response = await axios.get(`${API_URL}/students/${id}/formations`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching formations for student with ID ${id}:`, error);
      throw error;
    }
  }

  // Get student modules (UEs)
  async getStudentModules(id) {
    try {
      const response = await axios.get(`${API_URL}/students/${id}/ues`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching modules for student with ID ${id}:`, error);
      throw error;
    }
  }

  // Add student to group
  async addStudentToGroup(studentId, groupId) {
    try {
      const response = await axios.post(`${API_URL}/students/${studentId}/groupes/${groupId}`);
      return response.data;
    } catch (error) {
      console.error(`Error adding student ${studentId} to group ${groupId}:`, error);
      throw error;
    }
  }

  // Remove student from group
  async removeStudentFromGroup(studentId, groupId) {
    try {
      const response = await axios.delete(`${API_URL}/students/${studentId}/groupes/${groupId}`);
      return response.data;
    } catch (error) {
      console.error(`Error removing student ${studentId} from group ${groupId}:`, error);
      throw error;
    }
  }

  // Validate group inscription
  async validateStudentGroup(studentId, groupId, accept) {
    try {
      const response = await axios.put(`${API_URL}/students/${studentId}/groupes/${groupId}`, { accept });
      return response.data;
    } catch (error) {
      console.error(`Error validating group inscription for student ${studentId}, group ${groupId}:`, error);
      throw error;
    }
  }

  // Add student to formation
  async addStudentToFormation(studentId, formationId) {
    try {
      const response = await axios.post(`${API_URL}/students/${studentId}/formations/${formationId}`);
      return response.data;
    } catch (error) {
      console.error(`Error adding student ${studentId} to formation ${formationId}:`, error);
      throw error;
    }
  }

  // Remove student from formation
  async removeStudentFromFormation(studentId, formationId) {
    try {
      const response = await axios.delete(`${API_URL}/students/${studentId}/formations/${formationId}`);
      return response.data;
    } catch (error) {
      console.error(`Error removing student ${studentId} from formation ${formationId}:`, error);
      throw error;
    }
  }

  // Validate formation inscription
  async validateStudentFormation(studentId, formationId, accept) {
    try {
      const response = await axios.put(`${API_URL}/students/${studentId}/formations/${formationId}`, { accept });
      return response.data;
    } catch (error) {
      console.error(`Error validating formation inscription for student ${studentId}, formation ${formationId}:`, error);
      throw error;
    }
  }

  // Add student to UE
  async addStudentToUE(studentId, ueId) {
    try {
      const response = await axios.post(`${API_URL}/students/${studentId}/ues/${ueId}`);
      return response.data;
    } catch (error) {
      console.error(`Error adding student ${studentId} to UE ${ueId}:`, error);
      throw error;
    }
  }

  // Remove student from UE
  async removeStudentFromUE(studentId, ueId) {
    try {
      const response = await axios.delete(`${API_URL}/students/${studentId}/ues/${ueId}`);
      return response.data;
    } catch (error) {
      console.error(`Error removing student ${studentId} from UE ${ueId}:`, error);
      throw error;
    }
  }

  // Validate UE inscription
  async validateStudentUE(studentId, ueId, accept) {
    try {
      const response = await axios.put(`${API_URL}/students/${studentId}/ues/${ueId}`, { accept });
      return response.data;
    } catch (error) {
      console.error(`Error validating UE inscription for student ${studentId}, UE ${ueId}:`, error);
      throw error;
    }
  }
}

export default new StudentService();
