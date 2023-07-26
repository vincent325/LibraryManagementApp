export const oktaConfig = {
    clientId: '0oaagsjaj571F16Mi5d7',
    issuer: 'https://dev-45455834.okta.com/oauth2/default',
    redirectUri: 'http://localhost:3000/login/callback',
    scopes: ['openid', 'profile', 'email'],
    pkce: true,
    disableHttpsCheck: true,
}