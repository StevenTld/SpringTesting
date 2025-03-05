<template>
  <div class="academic-year-list">
    <div class="list-header">
      <h2>Academic Years List</h2>
      <router-link to="/academic-years/create" class="add-button">Add New Academic Year</router-link>
    </div>

    <div v-if="loading" class="loading">
      Loading academic years...
    </div>
    <div v-else-if="error" class="error">
      {{ error }}
    </div>
    <div v-else-if="academicYears.length === 0" class="empty-state">
      No academic years found. Add a new academic year to get started.
    </div>
    <div v-else class="academic-year-table-container">
      <table class="academic-year-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Formation Name</th>
            <th>TP Group Size</th>
            <th>TD Group Size</th>
            <th>Optional Units Count</th>
            <th>Responsible Email</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="academicYear in academicYears" :key="academicYear.id">
            <td>{{ academicYear.id }}</td>
            <td>{{ academicYear.formationName }}</td>
            <td>{{ academicYear.tpGroupSize }}</td>
            <td>{{ academicYear.tdGroupSize }}</td>
            <td>{{ academicYear.optionalTeachingUnitsCount }}</td>
            <td>{{ academicYear.responsibleEmail }}</td>
            <td class="actions">
              <router-link :to="`/academic-years/${academicYear.id}`" class="action-button view">View</router-link>
              <router-link :to="`/academic-years/${academicYear.id}/edit`" class="action-button edit">Edit</router-link>
              <button @click="confirmDelete(academicYear)" class="action-button delete">Delete</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Delete Confirmation Modal -->
    <div v-if="showDeleteModal" class="modal">
      <div class="modal-content">
        <h3>Confirm Delete</h3>
        <p>Are you sure you want to delete the academic year "{{ selectedAcademicYear?.formationName }}"?</p>
        <div class="modal-actions">
          <button @click="cancelDelete" class="cancel-button">Cancel</button>
          <button @click="deleteAcademicYear" class="delete-button">Delete</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import AcademicYearService from '@/services/AcademicYearService.js';

export default {
  name: 'AcademicYearList',
  data() {
    return {
      academicYears: [],
      loading: true,
      error: null,
      showDeleteModal: false,
      selectedAcademicYear: null
    };
  },
  created() {
    this.fetchAcademicYears();
  },
  methods: {
    async fetchAcademicYears() {
      this.loading = true;
      this.error = null;
      try {
        this.academicYears = await AcademicYearService.getAllAcademicYears();
      } catch (error) {
        this.error = 'Failed to load academic years. Please try again later.';
        console.error(error);
      } finally {
        this.loading = false;
      }
    },
    confirmDelete(academicYear) {
      this.selectedAcademicYear = academicYear;
      this.showDeleteModal = true;
    },
    cancelDelete() {
      this.showDeleteModal = false;
      this.selectedAcademicYear = null;
    },
    async deleteAcademicYear() {
      if (!this.selectedAcademicYear) return;
      
      try {
        await AcademicYearService.deleteAcademicYear(this.selectedAcademicYear.id);
        this.academicYears = this.academicYears.filter(
          year => year.id !== this.selectedAcademicYear.id
        );
        this.$emit('academic-year-deleted', this.selectedAcademicYear.id);
      } catch (error) {
        this.error = 'Failed to delete academic year. Please try again later.';
        console.error(error);
      } finally {
        this.showDeleteModal = false;
        this.selectedAcademicYear = null;
      }
    }
  }
};
</script>

<style scoped>
.academic-year-list {
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

.academic-year-table-container {
  overflow-x: auto;
}

.academic-year-table {
  width: 100%;
  border-collapse: collapse;
}

.academic-year-table th,
.academic-year-table td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #e9ecef;
}

.academic-year-table th {
  background-color: #f8f9fa;
  font-weight: bold;
}

.academic-year-table tr:hover {
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
