async function get1(cno) {

    const result = await axios.get(`/creplies/clist/${cno}`)
    //console.log(result)
    return result;
}

async function getList({cno, page, size, goLast}){

    const result = await axios.get(`/creplies/clist/${cno}`, {params: {page, size}})

    if(goLast){
        const total = result.data.total
        const lastPage = parseInt(Math.ceil(total/size))

        return getList({cno:cno, page:lastPage, size:size})

    }

    return result.data
}

async function addReply(replyObj) {
    const response = await axios.post(`/creplies/`,replyObj)
    return response.data
}

async function getReply(rno) {
    const response = await axios.get(`/creplies/${rno}`)
    return response.data
}

async function modifyReply(replyObj) {

    const response = await axios.put(`/creplies/${replyObj.rno}`, replyObj)
    return response.data
}

async function removeReply(rno) {
    const response = await axios.delete(`/creplies/${rno}`)
    return response.data
}