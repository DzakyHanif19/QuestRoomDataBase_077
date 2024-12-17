package com.example.project9.ui.viewmahasiswa
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.project9.ui.costumwidget.TopAppBar
import com.example.project9.ui.viewmodel.PenyediaViewModel
import com.example.roomlocaldb.ui.viewmodel.FormErrorState
import com.example.roomlocaldb.ui.viewmodel.MahasiswaEvent
import com.example.roomlocaldb.ui.viewmodel.MahasiswaViewModel
import com.example.roomlocaldb.ui.viewmodel.MhsUIState
import kotlinx.coroutines.launch

@Composable
fun InsertMhsView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MahasiswaViewModel = viewModel(factory = PenyediaViewModel.Factory) //Inisialisasi ViewModel
){
    val uiState = viewModel.uiState // Ambil UI State dari ViewModel
    val snackbarHostState = remember { SnackbarHostState()}
    val coroutineScope = rememberCoroutineScope()

    // Observasi perubahan snackbarMessage
    LaunchedEffect(uiState.snackBarMessage){
        uiState.snackBarMessage?.let {message ->
            coroutineScope.launch{
                //Tampilkan snackbar
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackbarMessage()
            }
        }
    }
    Scaffold(
        Modifier, snackbarHost = {SnackbarHost(hostState = snackbarHostState)}
    ) { padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Mahasiswa"
            )

            //Isi Body
            InsertBodyMhs(
                uiState = uiState,
                onValueChange = { updateEvent ->
                    // Update state di ViewModel
                    viewModel.updateUIState(updateEvent)
                },
                onClick = {
                    viewModel.saveData()
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun InsertBodyMhs(
    modifier: Modifier = Modifier,
    onValueChange: (MahasiswaEvent) -> Unit,
    uiState: MhsUIState,
    onClick: () -> Unit
){
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        FormMahasiswa(
            mahasiswaEvent = uiState.mahasiswaEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
        ){
            Text("Simpan")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormMahasiswa(
    mahasiswaEvent: MahasiswaEvent = MahasiswaEvent(),
    onValueChange: (MahasiswaEvent) -> Unit = {},
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
){
    val jenisKelamin = listOf("Laki-laki", "Perempuan")
    val kelas = listOf("A", "B", "C", "D", "E")

    Column(
        modifier = modifier.fillMaxWidth()
    ){
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.nama,
            onValueChange = { onValueChange(mahasiswaEvent.copy(nama = it))},
            label = { Text(text = "Nama") },
            isError = errorState.nama != null,
            placeholder = {Text(text = "Masukkan Nama")}
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.nim,
            onValueChange = { onValueChange(mahasiswaEvent.copy(nim = it))},
            label = { Text(text = "NIM") },
            isError = errorState.nim != null,
            placeholder = {Text(text = "Masukkan NIM")}
        )
        Text(
            text = errorState.nim ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jenis Kelamin")
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            jenisKelamin.forEach{ jk ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    RadioButton(
                        selected = mahasiswaEvent.jenisKelamin == jk,
                        onClick = {
                            onValueChange(mahasiswaEvent.copy(jenisKelamin = jk))},
                    )
                    Text(text = jk)
                }
            }
        }
        Text(
            text = errorState.jenisKelamin?:"",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.alamat,
            onValueChange = { onValueChange(mahasiswaEvent.copy(alamat = it))},
            label = { Text(text = "Alamat") },
            isError = errorState.alamat != null,
            placeholder = {Text(text = "Masukkan Alamat")}
        )
        Text(
            text = errorState.alamat ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Kelas")
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            kelas.forEach { kl ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = mahasiswaEvent.kelas == kl,
                        onClick = {
                            onValueChange(mahasiswaEvent.copy(kelas = kl))
                        },
                    )
                    Text(text = kl)
                }
            }
        }
        Text(
            text = errorState.kelas ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.angkatan,
            onValueChange = { onValueChange(mahasiswaEvent.copy(angkatan = it))},
            label = { Text(text = "angkatan") },
            isError = errorState.angkatan != null,
            placeholder = {Text(text = "Masukkan Angkatan")}
        )
        Text(
            text = errorState.angkatan ?: "",
            color = Color.Red
        )
    }
}