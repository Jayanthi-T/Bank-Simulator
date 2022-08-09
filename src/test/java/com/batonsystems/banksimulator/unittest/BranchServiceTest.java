package com.batonsystems.banksimulator.unittest;

import com.batonsystems.banksimulator.entity.Account;
import com.batonsystems.banksimulator.entity.Branch;
import com.batonsystems.banksimulator.entity.SortOrder;
import com.batonsystems.banksimulator.exception.AccountException;
import com.batonsystems.banksimulator.exception.BranchException;
import com.batonsystems.banksimulator.repository.BranchRepository;
import com.batonsystems.banksimulator.service.BranchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BranchServiceTest {
    @Autowired
    private BranchService branchService;
    @Autowired
    private BranchRepository branchRepository;

    //AddBranch Tests
    @DirtiesContext
    @Test
    public void testAddBranchWhenBranchIsNotNull() throws BranchException {

        Branch branch = new Branch("005943","GCT Branch","SBIN0005943","123456789","SBININBBXXX","Coimbatore","Thadagam Road");
        Branch actual = branchService.addBranch(branch,1);
        assertAll(
                ()->assertNotNull(actual),
                ()->assertNotNull(actual.getBranchCode()),
                ()->assertEquals("005943",actual.getBranchCode()),
                ()->assertEquals("GCT Branch",actual.getBranchName())
        );

    }
    @DirtiesContext
    @Test
    public void testAddBranchWhenBranchIsNull() throws BranchException{
        try {
            Branch branch = new Branch();
            Branch actual = branchService.addBranch(branch,1);
        }
        catch (BranchException exception){
            assertEquals("Branch code can not be null or empty.",exception.getMessage());
        }
    }

    //GetBranches Tests
    @Test
    @DirtiesContext
    public void testGetBranches() throws BranchException{
        Branch branch1 = new Branch("005941","GCT1 Branch","SBIN0005943","123456789","SBININBBXXX","Coimbatore","Thadagam Road");
        branchRepository.save(branch1);
        Branch branch2 = new Branch("005942","GCT2 Branch","SBIN0005943","123456789","SBININBBXXX","Coimbatore","Thadagam Road");
        branchRepository.save(branch2);
        Branch branch3 = new Branch("005943","GCT3 Branch","SBIN0005943","123456789","SBININBBXXX","Coimbatore","Thadagam Road");
        branchRepository.save(branch3);
        assertEquals(3,branchService.getBranches().size());
    }

    //GetBranchBYBranchCode Tests
    @Test
    public void testGetBranchByBranchCodeWhenBranchCodeIsNotNull() throws BranchException{
        Branch branch = new Branch("005943","GCT Branch","SBIN0005943","123456789","SBININBBXXX","Coimbatore","Thadagam Road");
        branchRepository.save(branch);
        Branch actual = branchService.getBranchByBranchCode("005943");
        assertEquals("GCT Branch",actual.getBranchName());
    }

    @Test
    @DirtiesContext
    public void testGetBranchByBranchCodeWhenBranchCodeIsNull() throws BranchException{
        try {
            Branch branch = new Branch();
            Branch actual = branchService.getBranchByBranchCode(branch.getBranchCode());
        }
        catch (BranchException exception){
            assertEquals("Branch code can not be null or empty.",exception.getMessage());
        }
    }

    @Test
    @DirtiesContext
    public void testGetBranchByBranchCodeWhenBranchCodeDoesnotExists() throws BranchException{
        try {
            Branch actual = branchService.getBranchByBranchCode("123456");
        }
        catch (BranchException exception){
            assertEquals("Such Branch code doesn't exist.",exception.getMessage());
        }
    }

    //GetBranchesBySortOrder Tests
    @Test
    @DirtiesContext
    public void testGetBranchesBySortOrderByBranchName() throws BranchException{
        Branch branch1 = new Branch("005943","MNOP Branch","SBIN0005943","123456789","SBININBBXXX","Covai","Thadagam Road");
        branchRepository.save(branch1);
        Branch branch2 = new Branch("005922","ABC Branch","SBIN0005943","123456789","SBININBBXXX","Covai","Thadagam1 Road");
        branchRepository.save(branch2);
        Branch branch3 = new Branch("005933","XYZ Branch","SBIN0005943","123456789","SBININBBXXX","Mysore","Thadagam 2Road");
        branchRepository.save(branch3);

        SortOrder sort = new SortOrder();
        sort.setSortOrder("asc");
        List<Branch> actualSortList = branchService.getBranchesBySortOrder("branchName",sort);

        List<Branch> expectedSortList = new ArrayList<>();
        expectedSortList.add(branch2);
        expectedSortList.add(branch1);
        expectedSortList.add(branch3);

        assertEquals(expectedSortList.size(),actualSortList.size());
        for(int i=0;i<expectedSortList.size();i++) {
            boolean isBranchCodeMatching= expectedSortList.get(i).getBranchCode() == actualSortList.get(i).getBranchCode()?true:false;
            boolean isBranchNameMatching=expectedSortList.get(i).getBranchName() == actualSortList.get(i).getBranchName()?true:false;
            assertAll(
                    ()->assertEquals(true,isBranchCodeMatching),
                    ()->assertEquals(true,isBranchNameMatching)
            );
        }
    }

    @Test
    @DirtiesContext
    public void testGetBranchesBySortOrderByBranchNameDesc() throws BranchException{
        Branch branch1 = new Branch("005943","MNOP Branch","SBIN0005943","123456789","SBININBBXXX","Covai","Thadagam Road");
        branchRepository.save(branch1);
        Branch branch2 = new Branch("005922","ABC Branch","SBIN0005943","123456789","SBININBBXXX","Covai","Thadagam1 Road");
        branchRepository.save(branch2);
        Branch branch3 = new Branch("005933","XYZ Branch","SBIN0005943","123456789","SBININBBXXX","Mysore","Thadagam 2Road");
        branchRepository.save(branch3);

        SortOrder sort = new SortOrder();
        sort.setSortOrder("desc");
        List<Branch> actualSortList = branchService.getBranchesBySortOrder("branchName",sort);

        List<Branch> expectedSortList = new ArrayList<>();
        expectedSortList.add(branch3);
        expectedSortList.add(branch1);
        expectedSortList.add(branch2);

        assertEquals(expectedSortList.size(),actualSortList.size());
        for(int i=0;i<expectedSortList.size();i++) {
            boolean isBranchCodeMatching= expectedSortList.get(i).getBranchCode() == actualSortList.get(i).getBranchCode()?true:false;
            boolean isBranchNameMatching=expectedSortList.get(i).getBranchName() == actualSortList.get(i).getBranchName()?true:false;
            assertAll(
                    ()->assertEquals(true,isBranchCodeMatching),
                    ()->assertEquals(true,isBranchNameMatching)
            );
        }
    }


    @Test
    @DirtiesContext
    public void testGetBranchesBySortOrderByBranchCode() throws BranchException{
        Branch branch1 = new Branch("005922","MNOP Branch","SBIN0005943","123456789","SBININBBXXX","Covai","Thadagam Road");
        branchRepository.save(branch1);
        Branch branch2 = new Branch("005911","ABC Branch","SBIN0005943","123456789","SBININBBXXX","Covai","Thadagam1 Road");
        branchRepository.save(branch2);
        Branch branch3 = new Branch("005933","XYZ Branch","SBIN0005943","123456789","SBININBBXXX","Mysore","Thadagam 2Road");
        branchRepository.save(branch3);

        SortOrder sort = new SortOrder();
        sort.setSortOrder("asc");
        List<Branch> actualSortList = branchService.getBranchesBySortOrder("branchCode",sort);

        List<Branch> expectedSortList = new ArrayList<>();
        expectedSortList.add(branch2);
        expectedSortList.add(branch1);
        expectedSortList.add(branch3);

        assertEquals(expectedSortList.size(),actualSortList.size());
        for(int i=0;i<expectedSortList.size();i++) {
            boolean isBranchCodeMatching= expectedSortList.get(i).getBranchCode() == actualSortList.get(i).getBranchCode()?true:false;
            boolean isBranchNameMatching=expectedSortList.get(i).getBranchName() == actualSortList.get(i).getBranchName()?true:false;
            assertAll(
                    ()->assertEquals(true,isBranchCodeMatching),
                    ()->assertEquals(true,isBranchNameMatching)
            );
        }
    }

    @Test
    @DirtiesContext
    public void testGetBranchesBySortOrderByBranchNameWhenSortOrderNotSpecified() throws BranchException{
        Branch branch1 = new Branch("005943","MNOP Branch","SBIN0005943","123456789","SBININBBXXX","Covai","Thadagam Road");
        branchRepository.save(branch1);
        Branch branch2 = new Branch("005922","ABC Branch","SBIN0005943","123456789","SBININBBXXX","Covai","Thadagam1 Road");
        branchRepository.save(branch2);
        Branch branch3 = new Branch("005933","XYZ Branch","SBIN0005943","123456789","SBININBBXXX","Mysore","Thadagam 2Road");
        branchRepository.save(branch3);

        SortOrder sort = new SortOrder();
        sort.setSortOrder("nothing");
        List<Branch> actualSortList = branchService.getBranchesBySortOrder("branchName",sort);

        List<Branch> expectedSortList = new ArrayList<>();
        expectedSortList.add(branch1);
        expectedSortList.add(branch2);
        expectedSortList.add(branch3);

        assertEquals(expectedSortList.size(),actualSortList.size());
        for(int i=0;i<expectedSortList.size();i++) {
            boolean isBranchCodeMatching= expectedSortList.get(i).getBranchCode() == actualSortList.get(i).getBranchCode()?true:false;
            boolean isBranchNameMatching=expectedSortList.get(i).getBranchName() == actualSortList.get(i).getBranchName()?true:false;
            assertAll(
                    ()->assertEquals(true,isBranchCodeMatching),
                    ()->assertEquals(true,isBranchNameMatching)
            );
        }
    }

    @Test
    @DirtiesContext
    public void testGetBranchesBySortOrderByBranchNameWhenSortFieldIsEmpty() throws BranchException{

        try {
        Branch branch1 = new Branch("005943","MNOP Branch","SBIN0005943","123456789","SBININBBXXX","Covai","Thadagam Road");
        branchRepository.save(branch1);
        Branch branch2 = new Branch("005922","ABC Branch","SBIN0005943","123456789","SBININBBXXX","Covai","Thadagam1 Road");
        branchRepository.save(branch2);
        Branch branch3 = new Branch("005933","XYZ Branch","SBIN0005943","123456789","SBININBBXXX","Mysore","Thadagam 2Road");
        branchRepository.save(branch3);

        SortOrder sort = new SortOrder();
        sort.setSortOrder("");
        List<Branch> actualSortList = branchService.getBranchesBySortOrder("branchName",sort);
        }
        catch (BranchException exception){
            assertEquals("Field or sortOrder cannot be empty.",exception.getMessage());
        }
    }



    //GetBranchesByCity Tests
    @Test
    @DirtiesContext
    public void testGetBranchesByCity() throws BranchException{
        Branch branch1 = new Branch("005943","GCT Branch","SBIN0005943","123456789","SBININBBXXX","Covai","Thadagam Road");
        branchRepository.save(branch1);
        Branch branch2 = new Branch("005922","GCT1 Branch","SBIN0005943","123456789","SBININBBXXX","Covai","Thadagam1 Road");
        branchRepository.save(branch2);
        Branch branch3 = new Branch("005933","GCT2 Branch","SBIN0005943","123456789","SBININBBXXX","Mysore","Thadagam 2Road");
        branchRepository.save(branch3);

        List<Branch> actual1 = branchService.getBranchesByCity("Covai");
        List<Branch> actual2 = branchService.getBranchesByCity("Mysore");
        assertAll(
                ()->assertEquals(2,actual1.size()),
                () -> assertEquals(1, actual2.size())
        );
    }

    @Test
    @DirtiesContext
    public void testGetBranchesByCityWhenCityIsNull() throws BranchException{
        try {
            List<Branch> actual1 = branchService.getBranchesByCity("");
        }
        catch (BranchException exception){
            assertEquals("Branch city cannot be empty.",exception.getMessage());
        }
    }

    //Update Branch Tests
    @Test
    @DirtiesContext
    public void testUpdateBranch() throws BranchException {
        Branch branch = new Branch(1L,"005943","GCT Branch","SBIN0005943","123456789","SBININBBXXX","Coimbatore","Thadagam Road");
        branchRepository.save(branch);
        Branch updatedBranch = new Branch(1L,"005943","GCT-PSG Branch","SBIN0005943","123456789","SBININBBXXX","Coimbatore","PSG Road");
        Branch actual = branchService.updateBranch(updatedBranch);
        assertAll(
                ()->assertNotNull(actual),
                ()->assertEquals("005943",actual.getBranchCode()),
                ()->assertEquals("GCT-PSG Branch",actual.getBranchName()),
                ()->assertEquals("PSG Road",actual.getBranchAddress())
        );
    }

    @Test
    @DirtiesContext
    public void testUpdateBranchWhenBranchCodeDoesnotExists() throws BranchException{
        try {
            Branch br = new Branch("123456","GCT Branch","SBIN0005943","123456789","SBININBBXXX","Coimbatore","Thadagam Road");
            Branch actual = branchService.updateBranch(br);
        }
        catch (BranchException exception){
            assertEquals("Such Branch code doesn't exist.",exception.getMessage());
        }
    }

    @DirtiesContext
    @Test
    public void testUpdateBranchWhenBranchIsNull() throws BranchException{
        try {
            Branch branch = new Branch();
            Branch actual = branchService.updateBranch(branch);
        }
        catch (BranchException exception){
            assertEquals("Branch code can not be null or empty.",exception.getMessage());
        }
    }

    //ActivateBranch Tests
    @Test
    @DirtiesContext
    public void testActivateBranchWhenBranchIsNotActive() throws BranchException{
        Branch branch1 = new Branch("0059423","GCT Branch","SBIN0005943","123456789","SBININBBXXX","Coimbatore","Thadagam Road");
        branchRepository.save(branch1);

        Boolean actual = branchService.activateBranch("0059423");
        assertEquals(true,actual);
    }

    @Test
    @DirtiesContext
    public void testActivateBranchWhenBranchIsActive() throws BranchException{
        Branch branch1 = new Branch("115948","GCT Branch","SBIN0005943","123456789","SBININBBXXX","Coimbatore","Thadagam Road");
        branch1.setActive(true);
        branchRepository.save(branch1);

        Boolean actual = branchService.activateBranch("115948");
        assertEquals(false,actual);
    }

    @Test
    @DirtiesContext
    public void testActivateBranchWhenBranchCodeDoesnotExists() throws BranchException{
        try {
            Boolean actual = branchService.activateBranch("123456");
        }
        catch (BranchException exception){
            assertEquals("Such Branch code doesn't exist.",exception.getMessage());
        }
    }

    @DirtiesContext
    @Test
    public void testActivateBranchWhenBranchIsNull() throws BranchException{
        try {
            Branch branch = new Branch();
            Boolean actual = branchService.activateBranch(branch.getBranchCode());
        }
        catch (BranchException exception){
            assertEquals("Branch code can not be null or empty.",exception.getMessage());
        }
    }

    //DeactivateBranch Tests
    @Test
    @DirtiesContext
    public void testDeactivateBranchWhenBranchIsActive() throws BranchException{
        Branch branch1 = new Branch("115948","GCT Branch","SBIN0005943","123456789","SBININBBXXX","Coimbatore","Thadagam Road");
        branch1.setActive(true);
        branchRepository.save(branch1);

        Boolean actual = branchService.deactivateBranch("115948");
        assertEquals(true,actual);
    }

    @Test
    @DirtiesContext
    public void testDeactivateBranchWhenBranchIsNotActive() throws BranchException{
        Branch branch1 = new Branch("115948","GCT Branch","SBIN0005943","123456789","SBININBBXXX","Coimbatore","Thadagam Road");
        branchRepository.save(branch1);

        Boolean actual = branchService.deactivateBranch("115948");
        assertEquals(false,actual);
    }

    @Test
    @DirtiesContext
    public void testDeactivateBranchWhenBranchCodeDoesnotExists() throws BranchException{
        try {
            Boolean actual = branchService.deactivateBranch("123456");
        }
        catch (BranchException exception){
            assertEquals("Such Branch code doesn't exist.",exception.getMessage());
        }
    }

    @DirtiesContext
    @Test
    public void testDeactivateBranchWhenBranchIsNull() throws BranchException{
        try {
            Branch branch = new Branch();
            Boolean actual = branchService.deactivateBranch(branch.getBranchCode());
        }
        catch (BranchException exception){
            assertEquals("Branch code can not be null or empty.",exception.getMessage());
        }
    }

    //DeleteBranch Tests
    @Test
    @DirtiesContext
    public void testDeleteBranchByBranchCodeWhenBranchCodeIsNotNull() throws BranchException{
        Branch branch1 = new Branch("115900","GCT Branch","SBIN0005943","123456789","SBININBBXXX","Coimbatore","Thadagam Road");
        branchRepository.save(branch1);

       branchService.deleteBranchByBranchCode("115900");
       assertEquals(false,branchService.isBranchExists("115900"));
    }

    @Test
    @DirtiesContext
    public void testDeleteBranchByBranchCodeWhenBranchCodeIsNull() throws BranchException{
        try {
            Branch branch = new Branch();
            branchService.deleteBranchByBranchCode(branch.getBranchCode());
        }
        catch (BranchException exception){
            assertEquals("Branch code can not be null or empty.",exception.getMessage());
        }
    }

    @Test
    @DirtiesContext
    public void testDeleteBranchWhenBranchCodeDoesnotExists() throws BranchException{
        try {
            branchService.deleteBranchByBranchCode("123456");
        }
        catch (BranchException exception){
            assertEquals("Such Branch code doesn't exist.",exception.getMessage());
        }
    }

    //IsBranchExists Test
    @DirtiesContext
    @Test
    public void testIsBranchExistsWhenBranchIsNull() throws BranchException{
        try {
            Branch branch = new Branch();
            Boolean actual = branchService.isBranchExists(branch.getBranchCode());
        }
        catch (BranchException exception){
            assertEquals("Branch code can not be null or empty.",exception.getMessage());
        }
    }

}
