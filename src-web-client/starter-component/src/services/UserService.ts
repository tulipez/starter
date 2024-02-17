import { createContext } from '@lit/context';
import { User } from '../model/User.js';

export class UserService {
	
	currentUser: User | undefined;
	
	login() {
		return new Promise<void>((resolve, reject) => {
			fetch('/api/user', {
				method: 'GET',
				headers: {
					'Content-Type': 'application/json',
				},
			})
	        .then(response => response.ok ? response.json() : undefined)
	        .then(data => {
	            this.currentUser = data;
	            resolve();
	        })
	        .catch(error => {
	            console.error('Error:', error);
	            reject(new Error("login failed"));
	        });
		});

		// eslint-disable-next-line no-promise-executor-return
		// await new Promise(resolve => setTimeout(resolve, 5000));
		/*
		this.currentUser = new User();
		this.currentUser.name = "zulut";
		this.currentUser.given_name = "zulut";
		this.currentUser.family_name = "zulut";
		this.currentUser.picture = "https://picsum.photos/18/18";
		this.currentUser.email = "zulut@zulut.fr";
		this.currentUser.email_verified = true;
		this.currentUser.locale = "fr";
		*/
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

