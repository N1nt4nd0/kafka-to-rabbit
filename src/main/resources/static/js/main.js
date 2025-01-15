let pageSize = parseInt(document.getElementById('page-size-input').value, 10);
let pageContentRequestUrl = '';
let fillTbodyFunction;
let updateIntervalId;
let currentPage = 0;
let lastPage = 0;

function updateData() {
    if (pageContentRequestUrl === '' || !fillTbodyFunction) {
        return;
    }
    fetch(`${pageContentRequestUrl}?page=${currentPage}&size=${pageSize}`)
        .then(response => {
            return response.json().then(data => {
                if (response.ok) {
                    return data;
                }
                throw {message: 'Bad response. Status: ' + response.status, details: data};
            });
        })
        .then(page => {
            pageButtonsDisableState(page.first, page.last, false);
            fillDataContainerDisableState(false);
            listButtonsDisableState(false);
            lastPage = page.totalPages > 0 ? page.totalPages - 1 : 0;
            document.getElementById('info-label').innerHTML = `
                Page: ${page.number} / ${lastPage}. 
                Page content: ${page.numberOfElements} / ${page.size}. 
                Total elements: ${page.totalElements}.
            `;
            document.getElementById('content-tbody').innerHTML = page.content
                .map(content => fillTbodyFunction(content))
                .join('');
        })
        .catch(error => {
            console.error('Error updating content: ', error);
            updateError(error);
        })
}

function updateError(error) {
    disableUi();
    clearTableData();
    document.getElementById('info-label').innerHTML = `
        <span style="color: red;">
        ${error.message}. Details in console.
        </span>`;
}

function changePage(pageNumber) {
    currentPage = pageNumber;
    startAutoUpdate();
}

function showLoading(loadingMessage) {
    disableUi();
    document.getElementById('info-label').innerHTML = `${loadingMessage}`
}

function disableUi() {
    pageButtonsDisableState(true, true, true);
    fillDataContainerDisableState(true);
    listButtonsDisableState(true);
}

function clearTableData() {
    document.getElementById('content-thead').innerHTML = '';
    document.getElementById('content-tbody').innerHTML = '';
}

function pageButtonsDisableState(firstState, lastState, sizeState) {
    [
        document.getElementById('prev-page-button'),
        document.getElementById('first-page-button')
    ]
        .forEach(btn => btn.disabled = firstState);
    [
        document.getElementById('next-page-button'),
        document.getElementById('last-page-button')
    ]
        .forEach(btn => btn.disabled = lastState);

    document.getElementById('set-page-size-button').disabled = sizeState;
    document.getElementById('page-size-input').disabled = sizeState;
}

function listButtonsDisableState(state) {
    [
        document.getElementById('person-list-button'),
        document.getElementById('company-list-button'),
        document.getElementById('hobby-list-button')
    ]
        .forEach(btn => btn.disabled = state);
}

function fillDataContainerDisableState(state) {
    [
        document.getElementById('fill-persons-button'),
        document.getElementById('fill-person-count-input'),
        document.getElementById('fill-person-hobby-max-count-input'),
        document.getElementById('truncate-persons-button'),
        document.getElementById('fill-companies-button'),
        document.getElementById('fill-company-count-input'),
        document.getElementById('truncate-companies-button'),
        document.getElementById('fill-hobbies-button'),
        document.getElementById('fill-hobby-count-input'),
        document.getElementById('truncate-hobbies-button')
    ]
        .forEach(elm => elm.disabled = state);
}

function loadPersonListTable() {
    showLoading("Loading person list...");
    clearTableData();
    document.title = 'Persons';
    document.getElementById('content-thead').innerHTML = `
        <tr>
            <th></th>
            <th>Email</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Company</th>
            <th>Hobbies</th>
        </tr>
        `;
    fillTbodyFunction = (personDto) => {
        const hobbies = personDto.hobbies.map(hobby => hobby.hobbyName).join('<br>');
        return `
        <tr>
            <td></td>
            <td>${personDto.email}</td>
            <td>${personDto.firstName}</td>
            <td>${personDto.lastName}</td>
            <td>${personDto.companyName}</td>
            <td>${hobbies}</td>
        </tr>
        `;
    };
    pageContentRequestUrl = document.getElementById('endpoints-container')
        .getAttribute('data-person-list-api-path');
    changePage(0);
}

function loadCompanyListTable() {
    showLoading("Loading company list...");
    clearTableData();
    document.title = 'Companies';
    document.getElementById('content-thead').innerHTML = `
        <tr>
            <th></th>
            <th>Company name</th>
        </tr>
        `;
    fillTbodyFunction = (companyDto) => {
        return `
        <tr>
            <td></td>
            <td>${companyDto.companyName}</td>
        </tr>
        `;
    };
    pageContentRequestUrl = document.getElementById('endpoints-container')
        .getAttribute('data-company-list-api-path');
    changePage(0);
}

function loadHobbyListTable() {
    showLoading("Loading hobby list...");
    clearTableData();
    document.title = 'Hobbies';
    document.getElementById('content-thead').innerHTML = `
        <tr>
            <th></th>
            <th>Hobby name</th>
        </tr>
        `;
    fillTbodyFunction = (hobbyDto) => {
        return `
        <tr>
            <td></td>
            <td>${hobbyDto.hobbyName}</td>
        </tr>
        `;
    };
    pageContentRequestUrl = document.getElementById('endpoints-container')
        .getAttribute('data-hobby-list-api-path');
    changePage(0);
}

function buildFillPersonsRequest() {
    const personCountParsed = parseInt(document.getElementById('fill-person-count-input').value, 10);
    const hobbyMaxCountParsed = parseInt(document.getElementById('fill-person-hobby-max-count-input').value, 10);
    return {
        personCount: isNaN(personCountParsed) ? 0 : personCountParsed,
        hobbyMaxCount: isNaN(hobbyMaxCountParsed) ? 0 : hobbyMaxCountParsed
    };
}

function buildFillCompaniesRequest() {
    const companyCountParsed = parseInt(document.getElementById('fill-company-count-input').value, 10);
    return {
        companyCount: isNaN(companyCountParsed) ? 0 : companyCountParsed
    };
}

function buildFillHobbiesRequest() {
    const hobbyCountParsed = parseInt(document.getElementById('fill-hobby-count-input').value, 10);
    return {
        hobbyCount: isNaN(hobbyCountParsed) ? 0 : hobbyCountParsed
    };
}

document.getElementById('fill-persons-button').onclick = () => {
    showLoading("Filling persons...");
    stopAutoUpdate();
    restApiRequest({
        url: document.getElementById('endpoints-container').getAttribute('data-person-fill-api-path'),
        method: 'POST',
        body: buildFillPersonsRequest(),
        errorMessage: 'Fill persons error occurred',
        callbackFunction: startAutoUpdate
    });
}

document.getElementById('truncate-persons-button').onclick = () => {
    showLoading("Truncating persons...");
    stopAutoUpdate();
    restApiRequest({
        url: document.getElementById('endpoints-container').getAttribute('data-person-truncate-api-path'),
        method: 'POST',
        errorMessage: 'Clear persons error occurred',
        callbackFunction: startAutoUpdate
    });
}

document.getElementById('fill-companies-button').onclick = () => {
    showLoading("Filling companies...");
    stopAutoUpdate();
    restApiRequest({
        url: document.getElementById('endpoints-container').getAttribute('data-company-fill-api-path'),
        method: 'POST',
        body: buildFillCompaniesRequest(),
        successMessage: 'Companies filled successfully',
        errorMessage: 'Fill companies error occurred',
        callbackFunction: startAutoUpdate
    });
}

document.getElementById('truncate-companies-button').onclick = () => {
    showLoading("Truncating companies...");
    stopAutoUpdate();
    restApiRequest({
        url: document.getElementById('endpoints-container').getAttribute('data-company-truncate-api-path'),
        method: 'POST',
        errorMessage: 'Clear companies error occurred',
        callbackFunction: startAutoUpdate
    });
}

document.getElementById('fill-hobbies-button').onclick = () => {
    showLoading("Filling hobbies...");
    stopAutoUpdate();
    restApiRequest({
        url: document.getElementById('endpoints-container').getAttribute('data-hobby-fill-api-path'),
        method: 'POST',
        body: buildFillHobbiesRequest(),
        successMessage: 'Hobbies filled successfully',
        errorMessage: 'Fill hobbies error occurred',
        callbackFunction: startAutoUpdate
    });
}

document.getElementById('truncate-hobbies-button').onclick = () => {
    showLoading("Truncating hobbies...");
    stopAutoUpdate();
    restApiRequest({
        url: document.getElementById('endpoints-container').getAttribute('data-hobby-truncate-api-path'),
        method: 'POST',
        errorMessage: 'Clear hobbies error occurred',
        callbackFunction: startAutoUpdate
    });
}

document.getElementById('prev-page-button').onclick = () => {
    changePage(currentPage - 1);
};

document.getElementById('next-page-button').onclick = () => {
    changePage(currentPage + 1);
};

document.getElementById('last-page-button').onclick = () => {
    changePage(lastPage);
};

document.getElementById('first-page-button').onclick = () => {
    changePage(0);
};

document.getElementById('set-page-size-button').onclick = () => {
    pageSize = parseInt(document.getElementById('page-size-input').value, 10);
    changePage(0);
};

document.getElementById('person-list-button').onclick = () => {
    loadPersonListTable();
};

document.getElementById('company-list-button').onclick = () => {
    loadCompanyListTable();
};

document.getElementById('hobby-list-button').onclick = () => {
    loadHobbyListTable();
};

function startAutoUpdate() {
    updateData();
    if (!updateIntervalId) {
        updateIntervalId = setInterval(updateData, parseInt(document.getElementById('web-config-container')
            .getAttribute('data-page-update-interval'), 10));
    }
}

function stopAutoUpdate() {
    if (updateIntervalId) {
        clearInterval(updateIntervalId);
        updateIntervalId = null;
    }
}

startAutoUpdate();