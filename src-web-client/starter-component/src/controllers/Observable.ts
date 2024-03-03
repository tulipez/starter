import { IObserver } from './IObserver.js';

export class Observable<T> {

	private observers: Set<IObserver<T>>;
	
    constructor() {
        this.observers = new Set();
    }

    subscribe(observer: IObserver<T>) {
        this.observers.add(observer);
    }

    unsubscribe(observer: IObserver<T>) {
        this.observers.delete(observer);
    }

    notify(message: string, body?: T) {
        this.observers.forEach((observer) => {
            observer.notify(message, body);
        });
    }
	
}