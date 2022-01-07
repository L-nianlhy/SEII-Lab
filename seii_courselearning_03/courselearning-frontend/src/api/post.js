import axios from "axios";
import { POST_MODULE } from "./_prefix";
//帖子相关API 刘红宇 2021.06.18

/**
 * 按照帖子id获取某一帖子的要展示的信息 GET /post/postById/{postId}
 * @param {*} postId
 * @returns
 */
export const getPostById = postId => {
  return axios.get(`${POST_MODULE}/postById/${postId}`).then(res => {
    return res.data;
  });
};

/**
 *  获取一门课程的按发布时间倒序排序的所有帖子信息的列表 GET /post/postTime/{courseId}
 * @param {*} courseId
 * @returns
 */
export const getPostsOrderByPostTime = courseId => {
  return axios.get(`${POST_MODULE}/postTime/${courseId}`).then(res => {
    return res.data;
  });
};

/**
 * 获取一门课程的按最后回复时间倒序排序的所有帖子信息的列表 GET /post/commentTime/{courseId}
 * @param {*} courseId
 * @returns
 */
export const getPostsOrderByLastReplyTime = courseId => {
  return axios.get(`${POST_MODULE}/commentTime/${courseId}`).then(res => {
    return res.data;
  });
};

/**
 * 创建一个新的帖子 POST /post/create
 * @param {*} payload；postTime与latestReplyTime属性为null
 * @returns
 */
export const createPost = payload => {
  const {
    postTime,
    title,
    message,
    userId,
    userName,
    courseId,
    latestReplyTime
  } = payload;
  return axios
    .post(`${POST_MODULE}/create`, {
      postTime,
      title,
      message,
      userId,
      userName,
      courseId,
      latestReplyTime
    })
    .then(res => {
      return res.data;
    });
};
