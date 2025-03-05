// views/student/StudentsView.vue
<template>
  <div class="students-view">
    <h1>Students Management</h1>
    <StudentList @student-deleted="handleStudentDeleted" />
  </div>
</template>

<script>
import StudentList from '@/components/student/StudentList.vue';

export default {
  name: 'StudentsView',
  components: {
    StudentList
  },
  methods: {
    handleStudentDeleted(studentId) {
      this.$router.push('/students');
    }
  }
};
</script>

// views/student/StudentCreateView.vue
<template>
  <div class="student-create-view">
    <h1>Create New Student</h1>
    <StudentForm 
      :submit-label="'Create Student'" 
      @submit="createStudent" 
      @cancel="cancel" 
    />
  </div>
</template>

<script>
import StudentForm from '@/components/student/StudentForm.vue';
import StudentService from '@/services/StudentService.js';

export default {
  name: 'StudentCreateView',
  components: {
    StudentForm
  },
  data() {
    return {
      error: null
    };
  },
  methods: {
    async createStudent(studentData) {
      try {
        const createdStudent = await StudentService.createStudent(studentData);
        this.$router.push(`/students/${createdStudent.studentNumber}`);
      } catch (error) {
        this.error = 'Failed to create student. Please try again.';
        console.error(error);
      }
    },
    cancel() {
      this.$router.push('/students');
    }
  }
};
</script>

// views/student/StudentEditView.vue
<template>
  <div class="student-edit-view">
    <h1>Edit Student</h1>
    <div v-if="loading" class="loading">
      Loading student data...
    </div>
    <div v-else-if="error" class="error">
      {{ error }}
    </div>
    <div v-else-if="!student" class="not-found">
      Student not found.
    </div>
    <StudentForm 
      v-else
      :initial-student="student" 
      :submit-label="'Update Student'" 
      @submit="updateStudent" 
      @cancel="cancel" 
    />
  </div>
</template>

<script>
import StudentForm from '@/components/student/StudentForm.vue';
import StudentService from '@/services/StudentService.js';

export default {
  name: 'StudentEditView',
  components: {
    StudentForm
  },
  props: {
    id: {
      type: [Number, String],
      required: true
    }
  },
  data() {
    return {
      student: null,
      loading: true,
      error: null
    };
  },
  created() {
    this.fetchStudent();
  },
  methods: {
    async fetchStudent() {
      this.loading = true;
      this.error = null;
      try {
        this.student = await StudentService.getStudentById(this.id);
      } catch (error) {
        this.error = 'Failed to load student data. Please try again later.';
        console.error(error);
      } finally {
        this.loading = false;
      }
    },
    async updateStudent(studentData) {
      try {
        await StudentService.updateStudent(this.id, studentData);
        this.$router.push(`/students/${this.id}`);
      } catch (error) {
        this.error = 'Failed to update student. Please try again.';
        console.error(error);
      }
    },
    cancel() {
      this.$router.push(`/students/${this.id}`);
    }
  },
  watch: {
    id() {
      this.fetchStudent();
    }
  }
};
</script>

<style scoped>
.loading, .error, .not-found {
  text-align: center;
  padding: 20px;
  margin: 20px 0;
  background-color: #f8f9fa;
  border-radius: 8px;
}

.error {
  color: #dc3545;
}
</style>

// views/student/StudentDetailsView.vue
<template>
  <div class="student-details-view">
    <h1>Student Details</h1>
    <StudentDetails :id="id" />
  </div>
</template>

<script>
import StudentDetails from '@/components/student/StudentDetails.vue';

export default {
  name: 'StudentDetailsView',
  components: {
    StudentDetails
  },
  props: {
    id: {
      type: [Number, String],
      required: true
    }
  }
};
</script>
