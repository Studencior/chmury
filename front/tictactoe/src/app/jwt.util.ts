import { jwtDecode } from 'jwt-decode';

export function getPlayerIdFromToken(): number | null {
  const token = localStorage.getItem('jwtToken');
  if (!token) return null;

  try {
    const decodedToken: any = jwtDecode(token);
    console.log(decodedToken);
    return decodedToken.userId; // Replace 'userId' with the actual key used in your JWT payload
  } catch (error) {
    console.error('Failed to decode token', error);
    return null;
  }
}
export function getPlayerUsernameFromToken(): String | null {
  const token = localStorage.getItem('jwtToken');
  if (!token) return null;

  try {
    const decodedToken: any = jwtDecode(token);
    console.log(decodedToken);
    return decodedToken.sub; // Replace 'userId' with the actual key used in your JWT payload
  } catch (error) {
    console.error('Failed to decode token', error);
    return null;
  }
}
