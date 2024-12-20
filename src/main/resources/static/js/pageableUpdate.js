const variablesElement = document.getElementById('variables-element');
const firstPageButton = document.getElementById('first-page-button');
const prevPageButton = document.getElementById('prev-page-button');
const nextPageButton = document.getElementById('next-page-button');
const lastPageButton = document.getElementById('last-page-button');
const pageInfoLabel = document.getElementById('page-info-label');
const contentTbody = document.getElementById('content-tbody');

const updateInterval = parseInt(variablesElement.getAttribute('data-update-interval'), 10);
const pageSize = parseInt(variablesElement.getAttribute('data-page-size'), 10);
const contentApiPath = variablesElement.getAttribute('data-content-api-path');

let currentPage = parseInt(variablesElement.getAttribute('data-current-page'), 10);
let isUpdating = false;
let lastPage = 0;

function updateData() {

    if (isUpdating) return;
    isUpdating = true;

    const fetchUrl = `${contentApiPath}?page=${currentPage}&size=${pageSize}`;
    fetch(fetchUrl)
        .then(response => response.json())
        .then(data => {
            lastPage = data.totalPages - 1;

            pageInfoLabel.innerHTML = `
                Page: ${data.number} / ${lastPage}. 
                Page size: ${data.size}. 
                Page elements: ${data.numberOfElements}. 
                Total elements: ${data.totalElements}.
            `;

            changePageButtonsState(data.first, data.last);

            contentTbody.innerHTML = data.content
                .map(content => createContentRow(content))
                .join('');
        })
        .catch(error => {
            console.error('Error updating content:', error);
            showUpdateError(error);
        })
        .finally(() => isUpdating = false);
}

function showUpdateError(error) {
    changePageButtonsState(true, true);
    pageInfoLabel.innerHTML = `Error updating content: ` + error.message;
    contentTbody.innerHTML = '';
}

function changePageButtonsState(firstState, lastState) {
    [prevPageButton, firstPageButton].forEach(btn => btn.disabled = firstState);
    [nextPageButton, lastPageButton].forEach(btn => btn.disabled = lastState);
}

function changePage(newPage) {
    currentPage = newPage;
    updateData();
}

prevPageButton.addEventListener('click', () => changePage(currentPage - 1));
nextPageButton.addEventListener('click', () => changePage(currentPage + 1));
firstPageButton.addEventListener('click', () => changePage(0));
lastPageButton.addEventListener('click', () => changePage(lastPage));

setInterval(updateData, updateInterval);

updateData();