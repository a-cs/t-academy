import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { map, Observable, startWith } from 'rxjs';
import IClient from 'src/app/interfaces/IClient';
import { ClientService } from 'src/app/service/client.service';
import { SkuService } from 'src/app/service/sku.service';

import ISku from '../../interfaces/ISku';

@Component({
  selector: 'app-create-transaction',
  templateUrl: './create-transaction.component.html',
  styleUrls: ['./create-transaction.component.css']
})
export class CreateTransactionComponent implements OnInit {
  skus: ISku[]
  clients: IClient[]
  filteredSkus: Observable<ISku[]>
  filteredClients: Observable<IClient[]>
  firstSku?: ISku

  form: FormGroup

  constructor(
    private skuService:SkuService,
    private clientService:ClientService,
    private formBuilder: FormBuilder) { }

  ngOnInit(): void {

    this.skuService.get().subscribe(
      data => {
        this.onGetSkus(data)
      }
    )
    this.clientService.get().subscribe(
      data => {
        this.onGetClients(data)
      }
    )

    this.configureForm()


    // this.filteredSkus.subscribe(ele => this.firstSku = ele[0])

  }

  onGetSkus (data:ISku[]){
    this.skus = data
    this.filteredSkus = this.form.controls["sku"].valueChanges.pipe(
      startWith(''),
      map((value: any) => {
        const name = value = typeof value === 'string' ? value : value?.name;
        return name ? this._filterSkus(name as string) : this.skus.slice();
      }),
    );
  }

  private _filterSkus(name: string): ISku[] {
    const filterValue = name.toLowerCase();
    return this.skus.filter(sku => sku.name?.toLowerCase().includes(filterValue));
  }

  onGetClients (data:IClient[]){
    this.clients = data
    this.filteredClients = this.form.controls["client"].valueChanges.pipe(
      startWith(''),
      map((value: any) => {
        const name = value = typeof value === 'string' ? value : value?.name;
        return name ? this._filterClients(name as string) : this.clients.slice();
      }),
    );
  }

  private _filterClients(name: string): IClient[] {
    const filterValue = name.toLowerCase();
    return this.clients.filter(client => client.name?.toLowerCase().includes(filterValue));
  }

  configureForm() {
    this.form = this.formBuilder.group({
      sku: [null, [Validators.required]],
      client: [null, [Validators.required]],
      quantity: [0, [Validators.required]],
      type: ["", [Validators.required]]
    })
  }




  skuFallback(){}

  clientFallback(){}

  displaySku(sku: ISku): string {
    return sku && sku.name ? sku.name : '';
  }

  displayClient(client: IClient): string {
    return client && client.name ? client.name : '';
  }

  createTransaction(){
    console.log({sku: this.form.controls["sku"].value, client: this.form.controls["client"].value, quantity: this.form.controls["quantity"].value, type: this.form.controls["type"].value, })
  }
}
