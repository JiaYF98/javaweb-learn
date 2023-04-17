function delFruit(fid) {
    if (confirm("确认要删除吗")) {
        window.location.href = 'del.do?fid=' + fid;
    }
}