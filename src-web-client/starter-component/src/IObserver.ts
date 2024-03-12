export interface IObserver<T> {

	notify(message: string, body?: T): void	
	
}