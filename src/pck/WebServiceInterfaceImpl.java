package pck;

public class WebServiceInterfaceImpl implements WebServiceInterface
{
	   
	public static final int INF = 1000; //!< constanta pentru valoarea "infinit"
	
	// 3 tipuri de piese finale: Sa1, Sa2, Sa3
	public static final int TIP_Sa1 = 0;
	public static final int TIP_Sa2 = 1;
	public static final int TIP_Sa3 = 2;
	
	// 4 tipuri de piese initiale: A, B, C, D
	public static final int TIP_A = 0;
	public static final int TIP_B = 1;
	public static final int TIP_C = 2;
	public static final int TIP_D = 3;
	
	public int[] getDisp()
	{
		int m_statii = 4;
		int[] disp = new int[m_statii];
		
		// Presupunem toate statiile disponibile
		for (int i = 0; i < disp.length; ++i)
		{
			disp[i] = 1;
		}
		
		return disp;
	}

	public int[][][] getTimpPrelucrare()
	{
		int n_tipuri = 3;
		int m_statii = 4;
		int nr_mat = 4;
		
		// Initializare tablouri cu dimensiunile corespunzatoare
		int[][][] timp_prelucrare_S = new int[m_statii][n_tipuri][nr_mat];
		
		// Initializare timpi de prelucrare
		for (int i = 0; i < timp_prelucrare_S.length; ++i)
		{
			for (int j = 0; j < timp_prelucrare_S[i].length; ++j)
			{
				for (int k = 0; k < timp_prelucrare_S[i][j].length; ++k)
				{
					timp_prelucrare_S[i][j][k] = INF;
				}
			}
		}
		
		timp_prelucrare_S[1][TIP_Sa1][TIP_C] = 8; // Statia 2, Sa1, C
		timp_prelucrare_S[1][TIP_Sa1][TIP_D] = 10; // Statia 2, Sa1, D
		timp_prelucrare_S[2][TIP_Sa1][TIP_A] = 10; // Statia 3, Sa1, A
		timp_prelucrare_S[2][TIP_Sa1][TIP_B] = 5; // Statia 3, Sa1, B
		
		timp_prelucrare_S[1][TIP_Sa2][TIP_B] = 10; // Statia 2, Sa2, B
		timp_prelucrare_S[1][TIP_Sa2][TIP_C] = 12; // Statia 2, Sa2, C
		timp_prelucrare_S[1][TIP_Sa2][TIP_D] = 14; // Statia 2, Sa2, D
		timp_prelucrare_S[3][TIP_Sa2][TIP_A] = 15; // Statia 4, Sa2, A
		
		timp_prelucrare_S[0][TIP_Sa3][TIP_B] = 15; // Statia 1, Sa3, B
		timp_prelucrare_S[0][TIP_Sa3][TIP_C] = 16; // Statia 1, Sa3, C
		timp_prelucrare_S[0][TIP_Sa3][TIP_D] = 18; // Statia 1, Sa3, D
		timp_prelucrare_S[3][TIP_Sa3][TIP_A] = 14; // Statia 4, Sa3, A
		
		return timp_prelucrare_S;
	}

}