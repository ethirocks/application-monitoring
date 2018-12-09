import {NgModule} from '@angular/core';
import {RouterModule,Routes, ActivatedRoute} from '@angular/router';
import { ChartsModule } from 'ng2-charts';
import { AdventureTimeService } from '../../real-time/front-end1/services/adventure-time.service';

import { WarTempComponent } from "../war-temp/war-temp.component";
import { WarUsageComponent } from "../war-usage/war-usage.component";
import { WarCoresComponent } from "../war-cores/war-cores.component";
import { WarThreadComponent } from "../war-thread/war-thread.component";
import { WarNetworkComponent } from "../war-network/war-network.component";
import { WarMemoryComponent } from "../war-memory/war-memory.component";

const childroutes:Routes = [
    {path: 'war-temp/:userID/:appID' , component: WarTempComponent},
    {path: 'war-usage/:userID/:appID' , component : WarUsageComponent},
    {path : 'war-cores/:userID/:appID' , component : WarCoresComponent},
    {path : 'war-thread/:userID/:appID' , component : WarThreadComponent},
    {path: 'war-network/:userID/:appID' , component: WarNetworkComponent},
    {path : 'war-memory/:userID/:appID' , component: WarMemoryComponent}
];

@NgModule({
    imports:[
        RouterModule.forRoot(childroutes),
        ChartsModule
    ],
    exports:[
        RouterModule
    ],
    providers: [AdventureTimeService]
})

export class WarDisplayRoutingModule{};

export const routingComponent = [WarTempComponent,WarCoresComponent,WarMemoryComponent,WarNetworkComponent,WarThreadComponent,WarUsageComponent];