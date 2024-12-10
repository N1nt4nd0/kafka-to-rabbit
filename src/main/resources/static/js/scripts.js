function autoUpdatePageData() {
    fetch('${contentPath}?page=' + ${currentPage} + '&size=' + ${pageSize})
        .then(response => response.text())
        .then(html => {
            const newDoc = new DOMParser().parseFromString(html, 'text/html');
            document.getElementById('page-buttons').innerHTML = newDoc.getElementById('page-buttons').innerHTML;
            document.getElementById('page-info-label').innerHTML = newDoc.getElementById('page-info-label').innerHTML;
            document.getElementById('content-table').innerHTML = newDoc.getElementById('content-table').innerHTML;
            document.getElementById('page-input-field').max = newDoc.getElementById('page-input-field').max
        })
        .catch(error => {
            console.error('Error by updating content:', error);
        });
}
setInterval(autoUpdatePageData, ${updateInterval});