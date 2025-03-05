<template>
  <div class="student-list">
    <div class="list-header">
      <h2>Student List</h2>
      <router-link to="/students/create" class="add-button">Add New Student</router-link>
    </div>

    <div v-if="loading" class="loading">
      Loading students...
    </div>
    <div v-else-if="error" class="error">
      {{ error }}
    </div>
    <div v-else-if="students.length === 0" class="empty-state">
      No students found. Add a new student to get started.
    </div>
    <div v-else class="student-table-container">
      <table class="student-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Birth Date</th>
            <th>Academic Year</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="student in students" :key="student.studentNumber">
            <td>{{ student.studentNumber }}</td>
            <td>{{ student.firstName }}</td>
            <td>{{ student.lastName }}</td>
            <td>{{ student.mail }}</td>
            <td>{{ student.birthDate }}</td>
            <td>{{ student.academicYearId || 'Not assigned' }}</td>
            <td class="actions">
              <router-link :to="`/students/${student.studentNumber}`" class="action-button view">View</router-link>
              <router-link :to="`/students/${student.studentNumber}/edit`" class="action-button edit">Edit</router-link>
              <button @click="confirmDelete(student)" class="action-button delete">Delete</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Delete Confirmation Modal -->
    <div v-if="showDeleteModal" class="modal">
      <div class="modal-content">
        <h3>Confirm Delete</h3>
        <p>Are you sure you want to delete student {{ selectedStudent?.firstName }} {{ selectedStudent?.lastName }}?</p>
        <div class="modal-actions">
          <button @click="cancelDelete" class="cancel-button">Cancel</button>
          <button @click="deleteStudent" class="delete-button">Delete</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import StudentService from '@/services/StudentService.js';

export default {
  name: 'StudentList',
  data() {
    return {
      students: [],
      loading: true,
      error: null,
      showDeleteModal: false,
      selectedStudent: null
    };
  },
  created() {
    this.fetchStudents();
  },
  methods: {
    async fetchStudents() {
      this.loading = true;
      this.error = null;
      try {
        this.students = await StudentService.getAllStudents();
      } catch (error) {
        this.error = 'Failed to load students. Please try again later.';
        console.error(error);
      } finally {
        this.loading = false;
      }
    },
    confirmDelete(student) {
      this.selectedStudent = student;
      this.showDeleteModal = true;
    },
    cancelDelete() {
      this.showDeleteModal = false;
      this.selectedStudent = null;
    },
    async deleteStudent() {
      if (!this.selectedStudent) return;
      
      try {
        await StudentService.deleteStudent(this.selectedStudent.studentNumber);
        this.students = this.students.filter(
          student => student.studentNumber !== this.selectedStudent.studentNumber
        );
        this.$emit('student-deleted', this.selectedStudent.studentNumber);
      } catch (error) {
        this.error = 'Failed to delete student. Please try again later.';
        console.error(error);
      } finally {
        this.showDeleteModal = false;
        this.selectedStudent = null;
      }
    }
  }
};
</script>

<style scoped>
.student-list {
  margin-bottom: 30px;
}

.list-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.add-button {
  background-color: #28a745;
  color: white;
  padding: 8px 16px;
  border-radius: 4px;
  text-decoration: none;
  transition: background-color 0.3s;
}

.add-button:hover {
  background-color: #218838;
}

.loading, .error, .empty-state {
  text-align: center;
  padding: 20px;
  background-color: #f8f9fa;
  border-radius: 4px;
}

.error {
  color: #dc3545;
}

.student-table-container {
  overflow-x: auto;
}

.student-table {
  width: 100%;
  border-collapse: collapse;
}

.student-table th,
.student-table td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #e9ecef;
}

.student-table th {
  background-color: #f8f9fa;
  font-weight: bold;
}

.student-table tr:hover {
  background-color: #f8f9fa;
}

.actions {
  display: flex;
  gap: 8px;
}

.action-button {
  padding: 6px 12px;
  border-radius: 4px;
  text-decoration: none;
  cursor: pointer;
  font-size: 0.85rem;
  border: none;
}

.view {
  background-color: #17a2b8;
  color: white;
}

.edit {
  background-color: #ffc107;
  color: #212529;
}

.delete {
  background-color: #dc3545;
  color: white;
}

/* Modal styles */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 100;
}

.modal-content {
  background-color: white;
  padding: 30px;
  border-radius: 8px;
  width: 100%;
  max-width: 400px;
}

.modal-actions {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.cancel-button, .delete-button {
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
  border: none;
  font-weight: bold;
}

.cancel-button {
  background-color: #6c757d;
  color: white;
}

.delete-button {
  background-color: #dc3545;
  color: white;
}
</style>
