<template>
  <!--  讨论区页面  刘红宇 2021.07.01-->
  <div>
    <v-container>
      <v-row>
        <v-col>
          <div class="welcome">欢迎来到《{{ courseInfo.name }}》讨论区</div>
          <v-card-text class="welcome"></v-card-text>
          <v-btn
            @click="toPostCreate"
            color="indigo lighten-2"
            height="50"
            dark
            class="createPostBtn"
          >
            发布帖子
          </v-btn>
          <v-card-text></v-card-text>
          <v-btn
            style="color:  #7986cb;font-weight: 800"
            text
            @click="loadPostByTime"
            >按发布时间排序</v-btn
          >
          <v-btn
            style="color:  #7986cb;font-weight: 800"
            text
            @click="loadPostByComment"
            >按最后评论时间排序</v-btn
          >
          <v-card max-width="800">
            <v-toolbar color="indigo lighten-2" dark>
              <v-app-bar-nav-icon></v-app-bar-nav-icon>
              <v-toolbar-title>评论区帖子（第{{ page }}页）</v-toolbar-title>
              <v-spacer></v-spacer>
            </v-toolbar>
            <v-list subheader two-line>
              <v-list-item v-for="post in postsShow" :key="post.id">
                <v-list-item-content>
                  <v-list-item-title v-text="post.title"></v-list-item-title>
                  <li class="postMessage">发帖人：{{ post.userName }}</li>
                  <li class="postMessage">发帖时间：{{ post.postTime }}</li>
                  <li class="postMessage">
                    最后回复时间：{{ post.latestReplyTime }}
                  </li>
                </v-list-item-content>

                <v-list-item-action>
                  <v-btn icon>
                    <v-icon
                      color="grey lighten-1"
                      @click="toPostDetailPage(post.id)"
                      >mdi-more</v-icon
                    >
                  </v-btn>
                </v-list-item-action>
              </v-list-item>
            </v-list>
          </v-card>
          <v-card-text></v-card-text>
          <div class="pageDiv">
            <span class="toPage">前往第</span>
            <div v-for="num in numOfPages" :key="num" class="toPageBtn">
              <button class="pageButton" @click="changePages(num)">
                {{ num }}
              </button>
            </div>
            <span class="toPage">页</span>
          </div>
        </v-col>
        <v-col class="courseMessage">
          <form style="position: sticky;top: 0">
            <v-text-field
              v-model="courseInfo.name"
              label="课程名称"
              readonly
            ></v-text-field>
            <v-text-field
              v-model="courseInfo.id"
              label="课程ID"
              readonly
            ></v-text-field>
            <v-select
              :items="types"
              v-model="courseInfo.type"
              label="课程分类"
              readonly
            ></v-select>
            <v-textarea
              v-model="courseInfo.intro"
              label="课程简介"
              readonly
            ></v-textarea>
            <v-text-field
              v-model="courseInfo.school"
              label="所属学校"
              readonly
            ></v-text-field>
            <v-text-field
              v-model="courseInfo.createTime"
              label="创建时间"
              readonly
            ></v-text-field>
            <v-text-field
              v-model="courseInfo.teacherName"
              label="创建教师"
              readonly
            ></v-text-field>
          </form>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { getCourseById } from "@/api/course";
import { judgeStudent, judgeTeacher } from "@/util/auth";
import { getPostsOrderByLastReplyTime, getPostsOrderByPostTime } from "@/api/post";

export default {
  name: "DiscussionArea",
  data() {
    return {
      page: 1, //当前页数
      numOfPages: [],
      courseInfo: {}, //课程信息
      types: ["初级", "中级", "高级"],
      postsShow: [], //展示帖子
      posts: [] //课程所有帖子
    };
  },
  methods: {
    //加载课程
    loadCourse() {
      const { courseId } = this.$route.params;
      const uid = window.localStorage.getItem("userId");
      getCourseById({ uid, courseId }).then(res => {
        this.courseInfo = res;
        this.loadPostByTime();
      });
    },
    //按照发布时间加载帖子
    loadPostByTime() {
      const courseId = this.courseInfo.id;
      getPostsOrderByPostTime(courseId).then(res => {
        this.posts = res;
        this.numOfPages = [];
        let numOfPage = this.posts.length / 10;
        for (let i = 0; i < 10 && i < this.posts.length; i++) {
          this.postsShow.push(this.posts[i]);
        }
        for (let i = 1; i <= numOfPage + 1; i++) {
          this.numOfPages.push(i);
        }
        this.changePages(1);
      });
    },
    //按照最后评论时间加载帖子
    loadPostByComment() {
      const courseId = this.courseInfo.id;
      getPostsOrderByLastReplyTime(courseId).then(res => {
        this.posts = res;
        this.numOfPages = [];
        let numOfPage = this.posts.length / 10;
        for (let i = 0; i < 10 && i < this.posts.length; i++) {
          this.postsShow.push(this.posts[i]);
        }
        for (let i = 1; i <= numOfPage + 1; i++) {
          this.numOfPages.push(i);
        }
        this.changePages(1);
      });
    },
    //跳转到发布帖子界面
    toPostCreate() {
      if (judgeTeacher()) {
        this.$router.push({
          path: `/teacher/postCreate/${this.courseInfo.id}`
        });
      } else if (judgeStudent()) {
        this.$router.push({
          path: `/student/postCreate/${this.courseInfo.id}`
        });
      }
    },
    //跳转到帖子详情界面
    toPostDetailPage(postId) {
      if (judgeTeacher()) {
        this.$router.push({
          path: `/teacher/postDetail/${postId}`
        });
      } else if (judgeStudent()) {
        this.$router.push({
          path: `/student/postDetail/${postId}`
        });
      }
    },
    //更改当前页数
    changePages(num) {
      this.page = num;
      this.postsShow = [];
      for (let i = (num - 1) * 10; i < num * 10 && i < this.posts.length; i++) {
        this.postsShow.push(this.posts[i]);
      }
    }
  },
  mounted() {
    this.loadCourse();
  }
};
</script>

<style scoped>
.courseMessage {
  width: 400px;
  position: absolute;
  right: 50px;
}
.welcome {
  font-size: 200%;
  display: inline;
}
.createPostBtn {
  display: inline;
  position: absolute;
  left: 58.5%;
}
.toPage {
  font-size: 120%;
}
.toPageBtn {
  display: inline;
}
.pageButton {
  font-size: 120%;
  margin: 0 5px 0 5px;
  background-color: #7986cb;
  color: white;
  height: 40px;
  width: 40px;
  border-radius: 40px;
}
.pageDiv {
  position: absolute;
  left: 30%;
}
.postMessage {
  font-size: 80%;
  color: gray;
}
</style>
