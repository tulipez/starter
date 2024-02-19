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
	
	saveCurrentUser() {
		return new Promise<void>((resolve, reject) => {
			fetch('/api/user', {
				method: 'PUT',
				headers: { 'Content-Type': 'application/json' },
				body: JSON.stringify(this._currentUser)
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
	
	login() {
		window.location.href="/login";
	}
	
	logout() {
		window.location.href="/logout";
	}
	
	
	
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
export const userServiceContext = createContext<UserService>('userService');

