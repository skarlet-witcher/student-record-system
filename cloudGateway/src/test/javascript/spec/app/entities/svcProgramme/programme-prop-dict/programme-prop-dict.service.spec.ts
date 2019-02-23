/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { ProgrammePropDictService } from 'app/entities/svcProgramme/programme-prop-dict/programme-prop-dict.service';
import { IProgrammePropDict, ProgrammePropDict, ProgrammePropType } from 'app/shared/model/svcProgramme/programme-prop-dict.model';

describe('Service Tests', () => {
    describe('ProgrammePropDict Service', () => {
        let injector: TestBed;
        let service: ProgrammePropDictService;
        let httpMock: HttpTestingController;
        let elemDefault: IProgrammePropDict;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ProgrammePropDictService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new ProgrammePropDict(0, 0, ProgrammePropType.GENERAL, 0, 0, 'AAAAAAA', 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a ProgrammePropDict', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new ProgrammePropDict(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a ProgrammePropDict', async () => {
                const returnedFromService = Object.assign(
                    {
                        forEnrollYear: 1,
                        type: 'BBBBBB',
                        forYearNo: 1,
                        forSemesterNo: 1,
                        key: 'BBBBBB',
                        value: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of ProgrammePropDict', async () => {
                const returnedFromService = Object.assign(
                    {
                        forEnrollYear: 1,
                        type: 'BBBBBB',
                        forYearNo: 1,
                        forSemesterNo: 1,
                        key: 'BBBBBB',
                        value: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a ProgrammePropDict', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
