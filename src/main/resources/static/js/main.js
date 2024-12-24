let pageSize = parseInt(document.getElementById('page-size-input').value, 10);
let pageContentRequestUrl = '';
let isUpdating = false;
let fillTbodyFunction;
let currentPage = 0;
let lastPage = 0;

function updateData() {
    if (isUpdating || pageContentRequestUrl === '' || !fillTbodyFunction) {
        return;
    }
    isUpdating = true;
    fetch(`${pageContentRequestUrl}?page=${currentPage}&size=${pageSize}`)
        .then(response => response.json())
        .then(pageData => {
            pageButtonsDisableState(pageData.first, pageData.last);
            fillDataContainerDisableState(false);
            listButtonsDisableState(false);
            lastPage = pageData.totalPages > 0 ? pageData.totalPages - 1 : 0;
            document.getElementById('info-label').innerHTML = `
                Page: ${pageData.number} / ${lastPage}. 
                Page content: ${pageData.numberOfElements} / ${pageData.size}. 
                Total elements: ${pageData.totalElements}.
            `;
            document.getElementById('content-tbody').innerHTML = pageData.content
                .map(content => fillTbodyFunction(content))
                .join('');
        })
        .catch(error => {
            console.error('Error updating content: ', error);
            updateError(error);
        })
        .finally(() => isUpdating = false);
}

function updateError(error) {
    fillDataContainerDisableState(true);
    pageButtonsDisableState(true, true);
    listButtonsDisableState(true);
    document.getElementById('info-label').innerHTML = `<span style="color: red;">Error updating content` + error.message;
    document.getElementById('content-thead').innerHTML = '';
    document.getElementById('content-tbody').innerHTML = '';
}

function pageButtonsDisableState(firstState, lastState) {
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
    currentPage = 0;
}

function loadCompanyListTable() {
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
    currentPage = 0;
}

function loadHobbyListTable() {
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
    currentPage = 0;
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
    sendRestApiRequest({
        url: document.getElementById('endpoints-container').getAttribute('data-person-fill-api-path'),
        method: 'POST',
        body: buildFillPersonsRequest(),
        successMessage: 'Persons filled successfully',
        errorMessage: 'Fill persons error occurred',
        callbackFunction: loadPersonListTable
    });
}

document.getElementById('truncate-persons-button').onclick = () => {
    sendRestApiRequest({
        url: document.getElementById('endpoints-container').getAttribute('data-person-truncate-api-path'),
        method: 'POST',
        errorMessage: 'Clear persons error occurred',
        callbackFunction: loadPersonListTable
    });
}

document.getElementById('fill-companies-button').onclick = () => {
    sendRestApiRequest({
        url: document.getElementById('endpoints-container').getAttribute('data-company-fill-api-path'),
        method: 'POST',
        body: buildFillCompaniesRequest(),
        successMessage: 'Companies filled successfully',
        errorMessage: 'Fill persons error occurred',
        callbackFunction: loadCompanyListTable
    });
}

document.getElementById('truncate-companies-button').onclick = () => {
    sendRestApiRequest({
        url: document.getElementById('endpoints-container').getAttribute('data-company-truncate-api-path'),
        method: 'POST',
        errorMessage: 'Clear companies error occurred',
        callbackFunction: loadCompanyListTable
    });
}

document.getElementById('fill-hobbies-button').onclick = () => {
    sendRestApiRequest({
        url: document.getElementById('endpoints-container').getAttribute('data-hobby-fill-api-path'),
        method: 'POST',
        body: buildFillHobbiesRequest(),
        successMessage: 'Hobbies filled successfully',
        errorMessage: 'Fill persons error occurred',
        callbackFunction: loadHobbyListTable
    });
}

document.getElementById('truncate-hobbies-button').onclick = () => {
    sendRestApiRequest({
        url: document.getElementById('endpoints-container').getAttribute('data-hobby-truncate-api-path'),
        method: 'POST',
        errorMessage: 'Clear hobbies error occurred',
        callbackFunction: loadHobbyListTable
    });
}

document.getElementById('prev-page-button').onclick = () => {
    currentPage--;
    updateData();
};

document.getElementById('next-page-button').onclick = () => {
    currentPage++;
    updateData();
};

document.getElementById('last-page-button').onclick = () => {
    currentPage = lastPage;
    updateData();
};

document.getElementById('first-page-button').onclick = () => {
    currentPage = 0;
    updateData();
};

document.getElementById('set-page-size-button').onclick = () => {
    pageSize = parseInt(document.getElementById('page-size-input').value, 10);
    currentPage = 0;
    updateData();
};

document.getElementById('person-list-button').onclick = () => {
    loadPersonListTable();
    updateData();
};

document.getElementById('company-list-button').onclick = () => {
    loadCompanyListTable();
    updateData();
};

document.getElementById('hobby-list-button').onclick = () => {
    loadHobbyListTable();
    updateData();
};

setInterval(updateData, parseInt(document.getElementById('web-config-container').getAttribute('data-page-update-interval'), 10));

updateData();