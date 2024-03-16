import { html, LitElement } from 'lit';
import { state } from 'lit/decorators.js';
import { consume } from '@lit/context';

import { IObserver } from '../../IObserver.js';
import { WorkspaceService, workspaceServiceContext } from '../../services/WorkspaceService.js';
import { ActionService, actionServiceContext, EVENT_ACTION_CREATED } from '../../services/ActionService.js';
import { Action } from '../../model/Action.js';

import styles from './list-actions.styles.js';
import '../list-actions-element/list-actions-element.js';

export class TzListActions extends LitElement {
	
    static styles = styles;
    
    @consume({context: workspaceServiceContext})
    @state()
	private workspaceService?: WorkspaceService;
	
    @consume({context: actionServiceContext})
    @state()
	private actionService?: ActionService;

	private actionServiceObserver = <IObserver<Action>>{
	    // eslint-disable-next-line @typescript-eslint/no-unused-vars
	    notify: (message: string, action: Action) => {
			if(message === EVENT_ACTION_CREATED) {
				this.requestUpdate();
			}
	    }
	};
	
	connectedCallback() {
		super.connectedCallback();
		this.actionService?.subscribe(this.actionServiceObserver);
	}
	
	disconnectedCallback() {
		super.disconnectedCallback();
		this.actionService?.unsubscribe(this.actionServiceObserver);
	}
	
	// faire un DateUtils
	private getTodayActions() : Action[] {
		const currentWorkspace = this.workspaceService?.currentWorkspace;
		if(!currentWorkspace) return [];
		if(!currentWorkspace.actions) return [];

		const currentDate : Date = new Date();
		currentDate.setUTCHours(0,0,0,0);
		const currentDayEpochUTC = currentDate.getTime();
		
		return currentWorkspace.actions.filter(action => action.date === currentDayEpochUTC);
	}
	 
    render() {
        return html`
        ${this.getTodayActions()
        	.map(action =>
		    	html`<tz-list-actions-element .action=${action}></tz-list-actions-element>`
		    )}
        `;
    }
}
