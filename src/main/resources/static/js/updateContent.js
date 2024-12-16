const pageDataElement = document.getElementById('update-content-data');
const contentPath = pageDataElement.getAttribute('data-content-path');
const currentPage = parseInt(pageDataElement.getAttribute('data-current-page'), 10);
const pageSize = parseInt(pageDataElement.getAttribute('data-page-size'), 10);
const updateInterval = parseInt(pageDataElement.getAttribute('data-update-interval'), 10);

function updateData() {
    fetch(`${contentPath}?page=${currentPage}&size=${pageSize}`)
        .then(response => response.text())
        .then(html => {
            const newDoc = new DOMParser().parseFromString(html, 'text/html');
            document.getElementById('page-buttons').innerHTML = newDoc.getElementById('page-buttons').innerHTML;
            document.getElementById('page-info-label').innerHTML = newDoc.getElementById('page-info-label').innerHTML;
            document.getElementById('content-table').innerHTML = newDoc.getElementById('content-table').innerHTML;
        })
        .catch(error => console.error('Error by updating content:', error));
}

setInterval(updateData, updateInterval);
