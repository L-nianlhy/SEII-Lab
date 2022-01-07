import axios from "axios";
import { COMMENT_MODULE } from "./_prefix";
//评论相关API 刘红宇 2021.06.18

/**
 * 获取一个帖子的按发布时间倒序排序的所有评论的列表 GET /comment/{postId}
 * @param {*} postId
 * @returns
 */
export const getCommentsOrderByTime = postId => {
  return axios.get(`${COMMENT_MODULE}/${postId}`).then(res => {
    return res.data;
  });
};

/**
 * 获取用户按评论时间倒序排序的所有回帖通知信息的列表 GET /comment/noRead/{uid}
 * @param {*} uid
 * @returns
 */
export const getReplyNoticesByUser = uid => {
  return axios.get(`${COMMENT_MODULE}/noRead/${uid}`).then(res => {
    return res.data;
  });
};

/**
 * 设置某条回帖通知为已读 POST /comment/read/{commentId}
 * @param {*} commentId
 * @returns
 */
export const setHaveRead = commentId => {
  return axios.get(`${COMMENT_MODULE}/read/${commentId}`).then(res => {
    return res.data;
  });
};

/**
 * 创建一个新的评论 POST /comment/create
 * @param {*} payload
 * @returns
 */
export const createComment = payload => {
  const {
    createTime, //null
    content,
    postId,
    userId,
    userName,
    repliedId, //可能为null
    repliedUserName,
    repliedContent,
    haveRead
  } = payload;
  return axios
    .post(`${COMMENT_MODULE}/create`, {
      createTime,
      content,
      postId,
      userId,
      userName,
      repliedId,
      repliedUserName,
      repliedContent,
      haveRead
    })
    .then(res => {
      return res.data;
    });
};
