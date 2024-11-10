async function getList(cno) {
    const result = await axios.get(`/creplies/${cno}`);
    console.log(result.data);
    return result.data;
}

async function addReply(replyObj, cno) {
    const result = await axios.post(`/creplies/${cno}`, replyObj);
    return result.data;
}

async function addReReply(replyObj, parentId) {
    const response = await axios.post(`/creplies/${parentId}`, replyObj);
    return response.data;
}

async function getReply(rno) {
    const response = await axios.get(`/creplies/${replyObj.rno}`, replyObj);
    return response.data;
}

async function modifyReply(replyObj) {
    const response = await axios.put(`/creplies/${replyObj.rno}`, replyObj);
    return response.data;
}

async function removeReply(rno) {
    const response = await axios.remove(`/creplies/${rno}`);
    return response.data;
}