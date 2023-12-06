package com.electronic.voting.util;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PermissionConstants {
    public final static Long PERM0_1 = 1L;
    public final static Long PERM1_2 = 2L;
    public final static Long PERM2_4 = 4L;
    public final static Long PERM3_8 = 8L;
    public final static Long PERM4_16 = 16L;
    public final static Long PERM5_32 = 32L;
    public final static Long PERM6_64 = 64L;
    public final static Long PERM7_128 = 128L;
    public final static Long PERM8_256 = 256L;
    public final static Long PERM9_512 = 512L;
    public final static Long PERM10_1024 = 1024L;
    public final static Long PERM11_2048 = 2048L;
    public final static Long PERM12_4096 = 4096L;
    public final static Long PERM13_8192 = 8192L;
    public final static Long PERM14_16384 = 16384L;
    public final static Long PERM15_32768 = 32768L;
    public final static Long PERM16_65536 = 65536L;
    public final static Long PERM17_131072 = 131072L;
    public final static Long PERM18_262144 = 262144L;
    public final static Long PERM19_524288 = 524288L;
    public final static Long PERM20_1048576 = 1048576L;
    public final static Long PERM21_2097152 = 2097152L;
    public final static Long PERM22_4194304 = 4194304L;
    public final static Long PERM23_8388608 = 8388608L;
    public final static Long PERM24_16777216 = 16777216L;
    public final static Long PERM25_335544130 = 335544130L;
    public final static Long PERM26_671088260 = 671088260L;
    public final static Long PERM27_134217728 = 134217728L;
    public final static Long PERM28_2684355456 = 2684355456L;
    public final static Long PERM29_536870912 = 536870912L;
    public final static Long PERM30_1073741824 = 1073741824L;
    public final static Long PERM31_2147483648 = 2147483648L;
    public final static Long PERM32_4294967296 = 4294967296L;
    public final static Long PERM33_8589934592 = 8589934592L;
    public final static Long PERM34_17179869184 = 17179869184L;
    public final static Long PERM35_34359738368 = 34359738368L;
    public final static Long PERM36_68719476736 = 68719476736L;
    public final static Long PERM37_137438953472 = 137438953472L;
    public final static Long PERM38_274877906944 = 274877906944L;
    public final static Long PERM39_549755813888 = 549755813888L;
    public final static Long PERM40_1099511627776 = 1099511627776L;
    public final static Long PERM41_2199023255552 = 2199023255552L;
    public final static Long PERM42_4398046511104 = 4398046511104L;
    public final static Long PERM43_8796093022208 = 8796093022208L;
    public final static Long PERM44_17592186044416 = 17592186044416L;
    public final static Long PERM45_35184372088832 = 35184372088832L;
    public final static Long PERM46_70368744177664 = 70368744177664L;
    public final static Long PERM47_140737488355328 = 140737488355328L;
    public final static Long PERM48_281747976710656 = 281747976710656L;
    public final static Long PERM49_562949953421312 = 562949953421312L;
    public final static Long PERM50_1125899906842624 = 1125899906842624L;
    public final static Long PERM51_2251799813685248 = 2251799813685248L;
    public final static Long PERM52_4503599627370496 = 4503599627370496L;
    public final static Long PERM53_9007199254740992 = 9007199254740992L;
    public final static Long PERM54_18014398509481984 = 18014398509481984L;
    public final static Long PERM55_36028797018963968 = 36028797018963968L;
    public final static Long PERM56_72057594037927936 = 72057594037927936L;
    public final static Long PERM57_144115188075855872 = 144115188075855872L;
    public final static Long PERM58_288230376151711744 = 288230376151711744L;
    public final static Long PERM59_576460752303423488 = 576460752303423488L;
    public final static Long PERM60_1152921504606846976 = 1152921504606846976L;
    public final static Long PERM61_2305843009213693952 = 2305843009213693952L;
    public final static Long PERM_ADMIN = 4611686018427387903L;
    public final static Long PERM_PERMISSION = 4611686018427387904L;
    public final static Long PERM_FULL_ADMIN = Long.MAX_VALUE;

    public final static String PERMISSION_SYSTEM = "PERMISSION_SYSTEM";
    public final static String PERMISSION_ADD_EMPLOYEE = "PERMISSION_ADD_EMPLOYEE";
    public final static String PERMISSION_ADD_EDIT = "PERMISSION_ADD_EDIT";
    public final static String PERMISSION_SYSTEM_ADMIN = "PERMISSION_SYSTEM_ADMIN";

    public final static Long[] PERMISSIONS = {
            PERM0_1,
            PERM1_2 ,
            PERM2_4 ,
            PERM3_8,
            PERM4_16 ,
            PERM5_32,
            PERM6_64 ,
            PERM7_128 ,
            PERM8_256 ,
            PERM9_512 ,
            PERM10_1024 ,
            PERM11_2048 ,
            PERM12_4096 ,
            PERM13_8192 ,
            PERM14_16384 ,
            PERM15_32768 ,
            PERM16_65536 ,
            PERM17_131072 ,
            PERM18_262144 ,
            PERM19_524288 ,
            PERM20_1048576,
            PERM21_2097152 ,
            PERM22_4194304 ,
            PERM23_8388608 ,
            PERM24_16777216 ,
            PERM25_335544130 ,
            PERM26_671088260 ,
            PERM27_134217728 ,
            PERM28_2684355456 ,
            PERM29_536870912 ,
            PERM30_1073741824,
            PERM31_2147483648,
            PERM32_4294967296 ,
            PERM33_8589934592 ,
            PERM34_17179869184 ,
            PERM35_34359738368 ,
            PERM36_68719476736 ,
            PERM37_137438953472 ,
            PERM38_274877906944 ,
            PERM39_549755813888 ,
            PERM40_1099511627776 ,
            PERM41_2199023255552 ,
            PERM42_4398046511104 ,
            PERM43_8796093022208 ,
            PERM44_17592186044416 ,
            PERM45_35184372088832 ,
            PERM46_70368744177664 ,
            PERM47_140737488355328 ,
            PERM48_281747976710656 ,
            PERM49_562949953421312 ,
            PERM50_1125899906842624,
            PERM51_2251799813685248 ,
            PERM52_4503599627370496 ,
            PERM53_9007199254740992 ,
            PERM54_18014398509481984,
            PERM55_36028797018963968 ,
            PERM56_72057594037927936 ,
            PERM57_144115188075855872,
            PERM58_288230376151711744 ,
            PERM59_576460752303423488,
            PERM60_1152921504606846976 ,
            PERM61_2305843009213693952 ,
            PERM_ADMIN ,
            PERM_PERMISSION ,
            PERM_FULL_ADMIN ,
    };

}
