/* eslint-disable lines-between-class-members */
import { createContext } from '@lit/context';
import { User } from '../model/User.js';

export class UserService {
	
	private _currentUser: User | undefined;
	get currentUser(): User | undefined { return this._currentUser; }

	login() {
		
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
		
		/*
		return new Promise<void>(resolve => {
			this._currentUser = {
				name : "zulut",
				given_name : "zulut",
				family_name : "zulut",
				picture : "https://picsum.photos/18/18",
				email : "zulut@zulut.fr",
				email_verified : true,
				locale : "fr",
			} as User;
			resolve();
		});
		*/
	}
	
	saveCurrentUser() {
		return new Promise<void>((resolve, reject) => {
			fetch('/api/user', {
				method: 'PUT',
				headers: { 'Content-Type': 'application/json' },
				body: JSON.stringify(this.currentUser)
			})
	        .then(response => {
				if(!response.ok) reject(new Error("saveCurrentUser failed"));
				else resolve();
			})
	        .catch(error => {
	            console.error('Error:', error);
	            reject(new Error("saveCurrentUser failed"));
	        });
		});
	}
	
	logout() {
		return new Promise<void>((resolve, reject) => {
			fetch('/api/logout', {
				method: 'GET',
				headers: { 'Content-Type': 'application/json' }
			})
	        .then(response => response.ok ? response.json() : undefined)
	        .then(() => {
	            window.location.href = "/";
	        })
	        .catch(error => {
	            console.error('Error:', error);
	            reject(new Error("login failed"));
	        });
		});
	}
	
}
export const userServiceContext = createContext<UserService>('userService');

