/* eslint-disable lines-between-class-members */
import { createContext } from '@lit/context';
import { User } from '../model/User.js';

export class UserService {
	
	private _currentUser: User | undefined;
	get currentUser() { return this._currentUser; }
	
	loadCurrentUser() {
		return new Promise<void>((resolve, reject) => {
			fetch('/api/user', {
				method: 'GET',
				headers: { 'Content-Type': 'application/json' }
			})
	        .then(response => response.ok ? response.json() : undefined)
	        .then(data => {
	            this._currentUser = data;
	            resolve();
	        })
	        .catch(error => {
	            console.error('Error:', error);
	            reject(new Error("login failed"));
	        });
		});
	}
	
	login() {
		window.location.href="/login";
	}
	
	logout() {
		window.location.href="/logout";
	}
}
export const userServiceContext = createContext<UserService>('userService');

