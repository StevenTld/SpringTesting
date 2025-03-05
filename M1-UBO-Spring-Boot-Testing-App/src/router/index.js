import { createRouter, createWebHistory } from 'vue-router'

// Import Views
import HomeView from '../views/HomeView.vue'

// Student Views
import StudentsView from '../views/student/StudentsView.vue'
import StudentCreateView from '../views/student/StudentCreateView.vue'
import StudentEditView from '../views/student/StudentEditView.vue'
import StudentDetailsView from '../views/student/StudentDetailsView.vue'

// Academic Year Views
import AcademicYearsView from '../views/academicYear/AcademicYearsView.vue'
import AcademicYearCreateView from '../views/academicYear/AcademicYearCreateView.vue'
import AcademicYearEditView from '../views/academicYear/AcademicYearEditView.vue'
import AcademicYearDetailsView from '../views/academicYear/AcademicYearDetailsView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  // Student routes
  {
    path: '/students',
    name: 'students',
    component: StudentsView
  },
  {
    path: '/students/create',
    name: 'student-create',
    component: StudentCreateView
  },
  {
    path: '/students/:id',
    name: 'student-details',
    component: StudentDetailsView,
    props: true
  },
  {
    path: '/students/:id/edit',
    name: 'student-edit',
    component: StudentEditView,
    props: true
  },
  // Academic Year routes
  {
    path: '/academic-years',
    name: 'academic-years',
    component: AcademicYearsView
  },
  {
    path: '/academic-years/create',
    name: 'academic-year-create',
    component: AcademicYearCreateView
  },
  {
    path: '/academic-years/:id',
    name: 'academic-year-details',
    component: AcademicYearDetailsView,
    props: true
  },
  {
    path: '/academic-years/:id/edit',
    name: 'academic-year-edit',
    component: AcademicYearEditView,
    props: true
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
