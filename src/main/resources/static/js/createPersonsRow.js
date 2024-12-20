function createContentRow(personData) {
    const hobbies = personData.hobbies.map(hobby => hobby.hobbyName).join('<br>');
    return `
            <tr>
                <td></td>
                <td>${personData.email}</td>
                <td>${personData.firstName}</td>
                <td>${personData.lastName}</td>
                <td>${personData.companyName}</td>
                <td>${hobbies}</td>
            </tr>
        `;
}