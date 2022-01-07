import Vue from "vue";
import VueRouter from "vue-router";
// coupon
import AdminLayout from "@/layouts/adminLayout.vue";
import CouponList from "@/views/coupon/CouponList.vue";
import CouponCreate from "@/views/coupon/CouponCreate.vue";
import CouponDeliver from "@/views/coupon/CouponDeliver.vue";
// teacher
import TeacherLayout from "@/layouts/teacherLayout.vue";
import CourseList from "@/views/course/TeacherCourseList.vue";
import CourseCreate from "@/views/course/CourseCreate.vue";
import CourseEdit from "@/views/course/CourseEdit.vue";
import TeacherCenter from "@/views/user/TeacherUserCenter.vue";
// student
import StudentLayout from "@/layouts/studentLayout.vue";
import StudentCourseList from "@/views/course/StudentCourseList.vue";
import UserCenter from "@/views/user/StudentUserCenter.vue";
import HistoryOrder from "@/views/order/HistoryOrder.vue";
import CourseStudy from "@/views/course/CourseStudy.vue";
import CoursePeek from "@/views/course/CoursePeek.vue";
// default
import DefaultLayout from "@/layouts/defaultLayout.vue";
import Login from "@/views/user/Login.vue";
import Register from "@/views/user/Register.vue";
import Home from "@/views/user/Home.vue";
import DiscussionArea from "@/views/forum/CourseDiscussionArea";
import PostCreate from "@/views/forum/PostCreate";
import PostDetail from "@/views/forum/PostDetail";

// authentication
import { judgeTeacher, judgeStudent } from "@/util/auth";
import PostNoRead from "@/views/forum/ReplyNotice";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: DefaultLayout,
    children: [
      {
        path: "/",
        name: "Home",
        component: Home
      },
      {
        path: "login",
        name: "Login",
        component: Login
      },
      {
        path: "register",
        name: "Register",
        component: Register
      }
    ]
  },
  {
    path: "/coupon",
    name: "Admin",
    component: AdminLayout,
    children: [
      {
        path: "/",
        name: "CouponList",
        redirect: "coupon"
      },
      {
        path: "coupon",
        name: "CouponList",
        exact: true,
        component: CouponList
      },
      {
        path: "coupon/create",
        name: "CouponCreate",
        component: CouponCreate
      },
      {
        path: "coupon/deliver",
        name: "CouponDeliver",
        component: CouponDeliver
      }
    ]
  },
  {
    path: "/teacher",
    name: "Teacher",
    component: TeacherLayout,
    children: [
      {
        path: "/",
        name: "TeacherCourseList",
        component: CourseList
      },
      {
        path: "user/:userId",
        name: "TeacherCenter",
        component: TeacherCenter
      },
      {
        path: "create",
        name: "TeacherCourseCreate",
        component: CourseCreate
      },
      {
        path: "edit/:courseId",
        name: "TeacherCourseEdit",
        component: CourseEdit
      },
      {
        path: "discussion/:courseId",
        name: "TeacherDiscussion",
        component: DiscussionArea
      },
      {
        path: "postCreate/:courseId",
        name: "TeacherPostCreate",
        component: PostCreate
      },
      {
        path: "postDetail/:postId",
        name: "TeacherPostDetail",
        component: PostDetail
      },
      {
        path: "postNoRead",
        name: "TeacherPostNoRead",
        component: PostNoRead
      }
    ]
  },
  {
    path: "/student",
    name: "Student",
    component: StudentLayout,
    children: [
      {
        path: "/",
        name: "StudentCourseList",
        component: StudentCourseList
      },
      {
        path: "user/:userId",
        name: "StudentUserCenter",
        component: UserCenter
      },
      {
        path: "history",
        name: "HistoryOrder",
        component: HistoryOrder
      },
      {
        path: "course/:courseId",
        name: "CourseStudy",
        component: CourseStudy
      },
      {
        path: "peek/:courseId",
        name: "CoursePeek",
        component: CoursePeek
      },
      {
        path: "discussion/:courseId",
        name: "StudentDiscussion",
        component: DiscussionArea
      },
      {
        path: "postCreate/:courseId",
        name: "StudentPostCreate",
        component: PostCreate
      },
      {
        path: "postDetail/:postId",
        name: "StudentPostDetail",
        component: PostDetail
      },
      {
        path: "postNoRead",
        name: "StudentPostNoRead",
        component: PostNoRead
      }
    ]
  }
];

const router = new VueRouter({
  mode: "hash",
  base: process.env.BASE_URL,
  routes
});

router.beforeEach((to, from, next) => {
  let legal = false;
  if (!to.path.startsWith("/student") && !to.path.startsWith("/teacher"))
    legal = true;
  if (to.path.startsWith("/student") && judgeStudent()) legal = true;
  if (to.path.startsWith("/teacher") && judgeTeacher()) legal = true;
  if (to.name === "CoursePeek") legal = true;
  if (legal) {
    next();
  } else {
    next({ name: "Login" });
  }
});

export default router;
