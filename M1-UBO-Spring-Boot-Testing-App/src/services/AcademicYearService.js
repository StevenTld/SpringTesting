import axios from 'axios';

const API_URL = 'http://localhost:8080';

class AcademicYearService {
  // Get all academic years
  async getAllAcademicYears() {
    try {
      const response = await axios.get(`${API_URL}/academicyears`);
      return response.data;
    } catch (error) {
      console.error('Error fetching academic years:', error);
      throw error;
    }
  }

  // Get an academic year by ID
  async getAcademicYearById(id) {
    try {
      const response = await axios.get(`${API_URL}/academicyears/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching academic year with ID ${id}:`, error);
      throw error;
    }
  }

  // Create a new academic year
  async createAcademicYear(academicYear) {
    try {
      const response = await axios.post(`${API_URL}/academicyears`, academicYear);
      return response.data;
    } catch (error) {
      console.error('Error creating academic year:', error);
      throw error;
    }
  }

  // Update an academic year
  async updateAcademicYear(id, academicYear) {
    try {
      const response = await axios.put(`${API_URL}/academicyears/${id}`, academicYear);
      return response.data;
    } catch (error) {
      console.error(`Error updating academic year with ID ${id}:`, error);
      throw error;
    }
  }

  // Delete an academic year
  async deleteAcademicYear(id) {
    try {
      const response = await axios.delete(`${API_URL}/academicyears/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error deleting academic year with ID ${id}:`, error);
      throw error;
    }
  }
}

export default new AcademicYearService();
