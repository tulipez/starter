import { createContext } from '@lit/context';
import { User } from '../model/User.js';

export class UserService {
	
	currentUser: User | undefined;
	
	async login() {
		const response = await fetch('/api/user', {
			method: 'GET',
			headers: {
				'Content-Type': 'application/json',
			},
		});
		if (!response.ok) {
			console.log(await response.text());
		}
		else {
			this.currentUser = await response.json();
		}
	}
	
	async logout() {
		const response = await fetch('/api/logout', {
		method: 'GET',
			headers: {
				'Content-Type': 'application/json',
			},
		});
		if (!response.ok) {
			console.log(await response.text());
		}
		else {
			const resultLogout = await response.json();
			if(resultLogout && resultLogout.ok) {
				window.location.href = "/";
			}
			else {
				console.log(`error : ${JSON.stringify(resultLogout)}`);
			}
		}
	}
	
}
export const userServiceContext = createContext<UserService>('userService');

