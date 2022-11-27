import { Component, OnInit } from '@angular/core';
import IMeasurementUnit from 'src/app/interfaces/IMeasurementUnit';
import { MeasurementUnitService } from 'src/app/service/measurement-unit.service';

@Component({
  selector: 'app-measurement-unit',
  templateUrl: './measurement-unit.component.html',
  styleUrls: ['./measurement-unit.component.css']
})
export class MeasurementUnitComponent implements OnInit {

  measurementUnits: IMeasurementUnit[]
  searchText: string

  constructor(private measurementUnitService: MeasurementUnitService) { }

  ngOnInit(): void {
    this.measurementUnitService.get().subscribe(
      data => {
        this.measurementUnits = data
        console.log(this.measurementUnits)
    })
  }
  
  onSearchTextEntered(searchValue: string) {
    this.searchText = searchValue
    this.measurementUnitService.getByLikeName(this.searchText).subscribe(
      data => {this.measurementUnits = data
        console.log(this.measurementUnits)
      }
      )
    
}

}
